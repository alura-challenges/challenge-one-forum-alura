package com.br.alura.forum.domain.resposta;

import com.br.alura.forum.domain.topico.Topico;
import com.br.alura.forum.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mensagem;
	@ManyToOne
	@JoinColumn(name = "topico_id")
	private Topico topico;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	private Boolean solucao = false;

}
