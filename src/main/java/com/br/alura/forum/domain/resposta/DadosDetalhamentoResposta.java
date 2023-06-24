package com.br.alura.forum.domain.resposta;

import com.br.alura.forum.domain.topico.DadosListagemTopicos;
import com.br.alura.forum.domain.usuario.DadosListagemUsuarios;

import java.time.LocalDateTime;

public record DadosDetalhamentoResposta(
        Long id,
        String mensagem,
        DadosListagemTopicos topico,
        LocalDateTime dataCriacao,
        DadosListagemUsuarios autor,
        Boolean solucao,
        Boolean ativo
) {
    public DadosDetalhamentoResposta(Resposta resposta){
        this(resposta.getId(),
                resposta.getMensagem(),
                new DadosListagemTopicos(resposta.getTopico()),
                resposta.getDataCriacao(),
                new DadosListagemUsuarios(resposta.getAutor()),
                resposta.getSolucao(),
                resposta.getAtivo());
    }
}
