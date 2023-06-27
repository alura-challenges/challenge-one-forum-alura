package com.br.alura.forum.domain.resposta;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoResposta(
        @NotNull
        Long id,
        String mensagem

) {
}
