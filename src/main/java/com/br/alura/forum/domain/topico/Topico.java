package com.br.alura.forum.domain.topico;

import com.br.alura.forum.domain.curso.Curso;
import com.br.alura.forum.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@NoArgsConstructor
@AllArgsConstructor
public class Topico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	@Enumerated(value = EnumType.STRING)
	private StatusTopico status;
	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;
	private boolean ativo;
//	private List<Resposta> respostas = new ArrayList<>();

	public Topico(DadosCadastroTopico dados, Usuario autor){
		this.titulo = dados.titulo();
		this.mensagem = dados.mensagem();
		this.dataCriacao = LocalDateTime.now();
		this.status = StatusTopico.NAO_RESPONDIDO;
		this.autor = autor;
		this.curso = new Curso(dados.curso());
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
		Topico other = (Topico) obj;
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

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public StatusTopico getStatus() {
		return status;
	}

	public void setStatus(StatusTopico status) {
		this.status = status;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void atualiza(DadosAtualizacaoTopico dados){
		if(dados.titulo() != null){
			this.titulo = dados.titulo();
		}
		if(dados.mensagem() != null){
			this.mensagem = dados.mensagem();
		}
		if(dados.autor() != null){
			this.autor.atualizar(dados.autor());
		}
		if(dados.curso() != null){
			this.curso.atualizar(dados.curso());
		}
	}

	public void exclui(){
		this.ativo = false;
	}

	public void resolver(){
		this.status = StatusTopico.SOLUCIONADO;
	}

	public void fechar() {
		this.status = StatusTopico.FECHADO;
	}

	public void responder() {
		this.status = StatusTopico.NAO_SOLUCIONADO;
	}

	//	public List<Resposta> getRespostas() {
//		return respostas;
//	}

//	public void setRespostas(List<Resposta> respostas) {
//		this.respostas = respostas;
//	}

}
