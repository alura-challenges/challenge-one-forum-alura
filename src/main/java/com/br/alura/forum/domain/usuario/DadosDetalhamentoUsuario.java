package com.br.alura.forum.domain.usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String email,
        String senha,
        boolean ativo) {
    public DadosDetalhamentoUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.isAtivo());
    }
}
