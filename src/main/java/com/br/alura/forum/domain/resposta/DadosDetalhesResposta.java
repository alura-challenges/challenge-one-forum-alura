package com.br.alura.forum.domain.resposta;

import com.br.alura.forum.domain.topico.DadosDetalhesTopico;
import com.br.alura.forum.domain.topico.Topico;
import com.br.alura.forum.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DadosDetalhesResposta(
        Long id,
        @NotBlank(message = "A mensagem é obrigatória")
        String mensagem,
        Topico topico,
        LocalDateTime dataCriacao,
        Usuario autor,
        Boolean solucao) {


    public DadosDetalhesResposta(Resposta resposta) {
        this(resposta.getId(), resposta.getMensagem(), resposta.getTopico(), resposta.getDataCriacao(), resposta.getAutor(), resposta.getSolucao());
    }

}
