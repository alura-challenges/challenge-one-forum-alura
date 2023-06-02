package com.br.alura.forum.domain.resposta;

import com.br.alura.forum.domain.usuario.Usuario;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

public record DadosAtualizaResposta(
        Long id,
        String mensagem,
        @Column(name = "data_criacao")
        LocalDateTime dataCriacao,
        Usuario autor) {

    public DadosAtualizaResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao(), resposta.getAutor());
    }
}
