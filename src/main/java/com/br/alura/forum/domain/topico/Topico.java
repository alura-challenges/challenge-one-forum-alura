package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.resposta.Resposta;
import com.br.alura.forum.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensagem;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao = LocalDateTime.now();
	private StatusTopico status = StatusTopico.NAO_RESPONDIDO;
	@ManyToOne
	@JoinColumn(name = "usuarios_id")
	private Usuario autor;
	@ManyToOne
	@JoinColumn(name = "cursos_id")
	private Curso curso;

	@OneToMany(mappedBy = "topico")
	private List<Resposta> respostas = new ArrayList<>();

	public Topico(DadosCadastroTopico dados) {
		this.id = dados.id();
		this.titulo = dados.titulo();
		this.mensagem = dados.mensagem();
		this.dataCriacao = dados.dataCriacao();
		this.status = dados.status();
		this.autor = dados.autor();
		this.curso = dados.curso();
	}


	public void atualizarDados(DadosCadastroTopico dados) {
		if(dados.titulo() != null){
			this.titulo = dados.titulo();
		}
		if(dados.mensagem() != null){
			this.titulo = dados.mensagem();
		}
		if(dados.dataCriacao() != null){
			this.dataCriacao = dados.dataCriacao();
		}

	}

	public void adicionarResposta(Resposta resposta){
		if(!this.respostas.contains(resposta)){
			this.respostas.add(resposta);
		}
	}
}


