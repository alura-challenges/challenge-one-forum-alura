package com.br.alura.forum.domain.curso;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Curso")
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String categoria;

}
