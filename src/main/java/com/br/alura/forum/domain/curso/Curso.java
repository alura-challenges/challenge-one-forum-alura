package com.br.alura.forum.domain.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Entity(name = "Curso")
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String categoria;
	private boolean ativo;

	public Curso(DadosCadastroCurso dadosCadastroCurso) {
		this.nome = dadosCadastroCurso.nome();
		this.categoria = dadosCadastroCurso.categoria();
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
		Curso other = (Curso) obj;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public void atualizar(DadosAtualizacaoCurso dadosAtualizacaoCurso){
		if(dadosAtualizacaoCurso.nome() != null){
			this.nome = dadosAtualizacaoCurso.nome();
		}
		if (dadosAtualizacaoCurso.categoria() != null){
			this.categoria = dadosAtualizacaoCurso.categoria();
		}
		if (dadosAtualizacaoCurso.ativo() != this.ativo){
			this.ativo = dadosAtualizacaoCurso.ativo();
		}
	}

	public void excluir(){
		this.ativo = false;
	}
}
