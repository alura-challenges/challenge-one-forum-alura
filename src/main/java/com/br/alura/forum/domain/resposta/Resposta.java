package com.br.alura.forum.domain.resposta;

import com.br.alura.forum.domain.topico.Topico;
import com.br.alura.forum.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Resposta")
@Table(name = "respostas")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mensagem;
	@ManyToOne
	@JoinColumn(name = "topico_id")
	@JsonIgnore
	private Topico topico;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao = LocalDateTime.now();

	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	private Boolean solucao = false;

	public Resposta(DadosResposta dados) {
		this.id = dados.id();
		this.mensagem = dados.mensagem();
		this.topico = dados.topico();
		this.dataCriacao = dados.dataCriacao();
		this.autor = dados.autor();
		this.solucao = dados.solucao();
	}

	public void atualizar(DadosAtualizaResposta dados) {
		if(dados.mensagem()!= null){
			this.mensagem = dados.mensagem();
		}
	}
}
