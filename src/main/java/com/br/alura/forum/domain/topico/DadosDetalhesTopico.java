package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.resposta.DadosResposta;
import com.br.alura.forum.domain.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record DadosDetalhesTopico(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, StatusTopico status, Usuario usuario, Curso curso, List<DadosResposta> respostas) {

    public DadosDetalhesTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor(), topico.getCurso(),
                new ArrayList<>(
                        topico.getRespostas().stream().map(DadosResposta::new).collect(Collectors.toList())));
    }
}
