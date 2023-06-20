package com.br.alura.forum.domain.usuario;

public record DadosListagemUsuarios(
        Long id,
        String nome,
        String email
) {
    public DadosListagemUsuarios(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
