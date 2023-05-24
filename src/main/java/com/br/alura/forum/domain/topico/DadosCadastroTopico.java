package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.resposta.Resposta;
import com.br.alura.forum.domain.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public record DadosCadastroTopico(String titulo, String mensagem, LocalDateTime dataCriacao, StatusTopico status, Usuario autor, Curso curso, List<Resposta> respostas) {


}
