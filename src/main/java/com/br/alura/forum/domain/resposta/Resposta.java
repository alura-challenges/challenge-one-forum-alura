package com.br.alura.forum.domain.resposta;

import com.br.alura.forum.domain.topico.Topico;
import com.br.alura.forum.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Resposta")
@Table(name = "respostas")
@NoArgsConstructor
@AllArgsConstructor
public class Resposta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String mensagem;
	@ManyToOne
	@JoinColumn(name = "topico_id")
	private Topico topico;
	private LocalDateTime dataCriacao;
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	private Boolean solucao;
	private Boolean ativo;

	public Resposta(DadosCadastroResposta dados, Usuario autor, Topico topico) {
		this.mensagem = dados.mensagem();
		this.topico = topico;
		this.dataCriacao = LocalDateTime.now();
		this.autor = autor;
		this.solucao = false;
		this.ativo = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resposta other = (Resposta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Topico getTopico() {
		return topico;
	}

	public void setTopico(Topico topico) {
		this.topico = topico;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Boolean getSolucao() {
		return solucao;
	}

	public void setSolucao(Boolean solucao) {
		this.solucao = solucao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
}
