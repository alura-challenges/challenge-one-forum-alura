package com.br.alura.forum.domain.curso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    Curso findByNomeAndCategoria(String nome, String categoria);
    Page<Curso> findAllByAtivoTrue(Pageable paginacao);

    Page<Curso> findAllByAtivoTrueAndCategoria(String categoria, Pageable paginacao);
}
