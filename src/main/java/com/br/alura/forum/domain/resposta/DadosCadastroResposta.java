package com.br.alura.forum.domain.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroResposta(
        @NotBlank
        String mensagem,
        @NotNull
        Long topico,
        @NotNull
        Long autor

) {
}
