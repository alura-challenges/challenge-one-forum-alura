package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.resposta.DadosResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    public void validaDados(DadosResposta dados){
        // busca o topico a ser respondido pelo id
        var topicoRespondido =
                repository.getReferenceById(dados.topico().getId());
        if(topicoRespondido != null){
            //Passar qual o tópico que está sendo respondido
            dados.topico().setTitulo(topicoRespondido.getTitulo());
            dados.topico().setMensagem(topicoRespondido.getMensagem());
            dados.topico().setAutor(topicoRespondido.getAutor());
            dados.topico().setCurso(topicoRespondido.getCurso());
            dados.topico().setStatus(topicoRespondido.getStatus());

            this.topicoSolucionado(dados);
        }
    }

    public void salvar(Topico topico) {
        if(topico != null){
            topico.setDataCriacao(LocalDateTime.now());
            if(topico.getRespostas().isEmpty()){
                topico.setStatus(StatusTopico.NAO_RESPONDIDO);
            } else if (!topico.getRespostas().isEmpty()) {
                topico.setStatus(StatusTopico.NAO_SOLUCIONADO);
            }
            repository.save(topico);
        }
    }

    public Topico atualizar(DadosCadastroTopico dados) {
        var topico = repository.getReferenceById(dados.id());
        topico.atualizarDados(dados);
        return topico;
    }

    public void topicoSolucionado(DadosResposta dados) {
        Boolean solucao = dados.solucao();
        if(solucao.equals(true)){
            dados.topico().setStatus(StatusTopico.SOLUCIONADO);
        }
    }

    public void fecharTopico(DadosDetalhesTopico dados) {
        Topico topico = repository.getReferenceById(dados.id());
        StatusTopico status = dados.status();

        boolean temSolucao = topico.getRespostas().stream()
                .anyMatch(resposta -> resposta.getSolucao());
        if(temSolucao){
            excluir(topico);

        }
    }
    public void excluir(Topico topico) {
        topico.excluir();
    }
}
