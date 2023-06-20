package com.br.alura.forum.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCurso(
        @NotBlank
        String nome,
        @NotBlank
        String categoria) {

}
