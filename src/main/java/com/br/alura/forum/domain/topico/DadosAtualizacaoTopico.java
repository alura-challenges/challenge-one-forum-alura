package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.DadosAtualizacaoCurso;
import com.br.alura.forum.domain.usuario.DadosAtualizacaoUsuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
        @NotNull
        Long id,
        String titulo,
        String mensagem,
        @Valid
        DadosAtualizacaoUsuario autor,
        @Valid
        DadosAtualizacaoCurso curso) {
}
