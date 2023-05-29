package com.br.alura.forum.domain.resposta;

import com.br.alura.forum.domain.topico.Topico;
import com.br.alura.forum.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DadosResposta(
        Long id,
        String mensagem,
        LocalDateTime dataCriacao,
        Topico topico,
        Usuario autor
        ) {

    public DadosResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getDataCriacao(), resposta.getTopico(), resposta.getAutor());
    }
}
