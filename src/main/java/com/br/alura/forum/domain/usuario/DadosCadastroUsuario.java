package com.br.alura.forum.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        Long id,

        @NotBlank(message = "É obrigatório inserir o nome")
        String nome,

        @NotBlank(message = "Email obrigatório")
        @Email(message = "Formato inválido")
        String email,

        @NotBlank(message = "Senha obrigatória")
        String senha) {

    public DadosCadastroUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha());
    }
}
