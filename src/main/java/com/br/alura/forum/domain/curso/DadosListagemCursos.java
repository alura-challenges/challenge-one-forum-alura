package com.br.alura.forum.domain.curso;

public record DadosListagemCursos(
        Long id,
        String nome,
        String categoria) {
    public DadosListagemCursos(Curso curso){
        this(curso.getId(), curso.getNome(), curso.getCategoria());
    }
}
