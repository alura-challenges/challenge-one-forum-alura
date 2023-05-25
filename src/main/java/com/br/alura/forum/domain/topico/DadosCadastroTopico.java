package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.resposta.Resposta;
import com.br.alura.forum.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record DadosCadastroTopico(

        @NotBlank
        String mensagem,
        @NotBlank
        String titulo,
        LocalDateTime dataCriacao,
        StatusTopico status,
        @NotNull
        Usuario autor,
        @NotNull
        Curso curso,
        List<Resposta> respostas) {


}
