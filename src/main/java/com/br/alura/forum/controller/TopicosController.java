package com.br.alura.forum.controller;

import com.br.alura.forum.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService service;


    /**
     * Conforme o protocolo HTTP, ao cadastrarmos uma informação ou recurso em uma API,
     * o código HTTP que deve ser devolvido, neste cenário, é o código 201 chamado created.
     * Esse código significa que um registro foi criado na API.
     * Código 201: devolve no corpo da resposta os dados do novo recurso/registro criado
     * e um cabeçalho do protocolo HTTP (Location).
     * Este código 201 possui algumas regras:
     * Na resposta, devemos colocar o código 201 e
     * no corpo da resposta os dados do novo registro criado e,
     * também, um cabeçalho do protocolo HTTP.
     *
     * */
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder builder){
        var topico = new Topico(dados);
        service.salvar(topico);
        var uri = builder.path("topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhesTopico>> listar(@PageableDefault(size = 10, sort = {"curso"}) Pageable paginacao){
        var page = topicoRepository.findAll(paginacao).map(DadosDetalhesTopico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosCadastroTopico dados){
        Topico topico = service.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhesTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){

        var topico = topicoRepository.getReferenceById(id);
        service.excluir(topico); //exclusão lógica
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhesTopico(topico));
    }

    @PutMapping("/fechar")
    @Transactional
    public ResponseEntity<?> fechar(@RequestBody @Valid DadosDetalhesTopico dados){
        service.fecharTopico(dados);
        return ResponseEntity.noContent().build();
    }

}
