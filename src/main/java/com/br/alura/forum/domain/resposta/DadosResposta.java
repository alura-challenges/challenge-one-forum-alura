package com.br.alura.forum.domain.resposta;

import com.br.alura.forum.domain.topico.Topico;

import java.time.LocalDateTime;

public record DadosResposta(
        Long id,
        String mensagem,
        LocalDateTime dataCriacao,
        String nomeAutor) {

    public DadosResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao(), resposta.getAutor().getNome());
    }
}
