package com.br.alura.forum.domain.resposta;

import com.br.alura.forum.domain.topico.Topico;
import com.br.alura.forum.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    Page<Resposta> findAllByAtivoTrue(Pageable paginacao);
    Page<Resposta> findAllByAtivoTrueAndAutorAndTopico(Usuario autor, Topico topico, Pageable paginacao);
    Page<Resposta> findAllByAtivoTrueAndAutor(Usuario autor, Pageable paginacao);
    Page<Resposta> findAllByAtivoTrueAndTopico(Topico topico, Pageable paginacao);
}
