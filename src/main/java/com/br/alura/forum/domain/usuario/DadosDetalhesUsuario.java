package com.br.alura.forum.domain.usuario;

public record DadosDetalhesUsuario(
        Long id,
        String nome,
        String email) {
    public DadosDetalhesUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
