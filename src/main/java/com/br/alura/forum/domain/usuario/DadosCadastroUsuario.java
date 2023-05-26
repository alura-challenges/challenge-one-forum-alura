package com.br.alura.forum.domain.usuario;

public record DadosCadastroUsuario(Long id, String nome, String email) {

    public DadosCadastroUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
