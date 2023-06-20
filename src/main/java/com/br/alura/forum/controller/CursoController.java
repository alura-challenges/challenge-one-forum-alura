package com.br.alura.forum.controller;

import com.br.alura.forum.domain.curso.*;
import jakarta.transaction.Transactional;
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
    public ResponseEntity<DadosDetalhamentoCurso> cadastrar(@RequestBody DadosCadastroCurso dadosCadastroCurso, UriComponentsBuilder uriBuilder){
        var curso = new Curso(dadosCadastroCurso);
        cursoRepository.save(curso);
        var uri = uriBuilder.path("cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoCurso(curso));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoCurso>> listar(@PageableDefault(page = 0, size = 10, sort = {"nome"}, direction = Sort.Direction.ASC) Pageable paginacao){
        var cursos = cursoRepository.findAll(paginacao).map(DadosDetalhamentoCurso::new);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCurso> detalhar(@PathVariable Long id){
        var curso = cursoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCurso> atualizar(@RequestBody DadosAtualizacaoCurso dadosAtualizacaoCurso){
        var curso = cursoRepository.getReferenceById(dadosAtualizacaoCurso.id());
        curso.atualizar(dadosAtualizacaoCurso);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
