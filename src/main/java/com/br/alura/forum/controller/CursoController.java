package com.br.alura.forum.controller;

import com.br.alura.forum.domain.curso.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroCurso dadosCadastroCurso, UriComponentsBuilder uriBuilder){
        if (cursoRepository.findByNomeAndCategoria(dadosCadastroCurso.nome(), dadosCadastroCurso.categoria()) == null){
            var curso = new Curso(dadosCadastroCurso);
            cursoRepository.save(curso);
            var uri = uriBuilder.path("cursos/{id}").buildAndExpand(curso.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoCurso(curso));
        }
        return ResponseEntity.badRequest().body("Curso duplicado!");
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemCursos>> listar(@RequestParam(required = false) String categoria,
            @PageableDefault(page = 0, size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao){
        Page<Curso> paginaCursos;

        if(categoria != null){
            paginaCursos = cursoRepository.findAllByAtivoTrueAndCategoria(categoria, paginacao);
        }
        else{
            paginaCursos = cursoRepository.findAllByAtivoTrue(paginacao);
        }
        return ResponseEntity.ok(paginaCursos.map(DadosListagemCursos::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCurso> detalhar(@PathVariable Long id){
        var curso = cursoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCurso> atualizar(@RequestBody @Valid DadosAtualizacaoCurso dadosAtualizacaoCurso){
        var curso = cursoRepository.getReferenceById(dadosAtualizacaoCurso.id());
        curso.atualizar(dadosAtualizacaoCurso);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        var curso = cursoRepository.getReferenceById(id);
        curso.excluir();
        return ResponseEntity.noContent().build();
    }

}
