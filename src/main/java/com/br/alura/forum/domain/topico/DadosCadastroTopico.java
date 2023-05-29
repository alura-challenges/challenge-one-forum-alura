package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.usuario.DadosCadastroUsuario;
import com.br.alura.forum.domain.usuario.Usuario;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record DadosCadastroTopico(

        Long id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,

        @Column(name = "data_criacao")
        LocalDateTime dataCriacao,

        StatusTopico status,

        Usuario autor,

        Curso curso
) {
        public DadosCadastroTopico(Topico topico) {
                this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor(), topico.getCurso());
        }
}
