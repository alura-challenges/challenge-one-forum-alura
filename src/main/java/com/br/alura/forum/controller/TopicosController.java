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
        topico.setDataCriacao(LocalDateTime.now());
        topico.setStatus(StatusTopico.NAO_RESPONDIDO);
        topicoRepository.save(topico);

        var uri = builder.path("topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhesTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listar(@PageableDefault(size = 10, sort = {"curso"}) Pageable paginacao){
        var page = topicoRepository.findAll(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosCadastroTopico dados){
        var topico = topicoRepository.getReferenceById(dados.id());
        topico.atualizarDados(dados);

        //Não é boa prática devolver uma entidade JPA.
        // Precisa devolver todos os dados do médico.
        // Para isso, precisa de um DTO específico para isso.
        return ResponseEntity.ok(new DadosDetalhesTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhesTopico(topico));
    }

}
