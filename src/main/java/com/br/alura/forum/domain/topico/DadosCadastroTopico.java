package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.DadosCadastroCurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroTopico(
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        @NotNull
        Long autor,
        @NotNull
        DadosCadastroCurso curso) {
}
