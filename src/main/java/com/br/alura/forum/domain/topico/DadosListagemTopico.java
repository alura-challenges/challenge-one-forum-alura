package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DadosListagemTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        StatusTopico status,
        Usuario autor,
        Curso curso
) {

    public DadosListagemTopico(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor(), topico.getCurso());
    }
}
