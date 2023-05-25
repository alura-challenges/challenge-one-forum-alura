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

import java.util.Optional;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroTopico dados){
        topicoRepository.save(new Topico(dados));
    }

    @GetMapping
    public Page<DadosListagemTopico> listar(@PageableDefault(size = 10, sort = {"curso"}) Pageable paginacao){
        return topicoRepository.findAll(paginacao).map(DadosListagemTopico::new);
    }

    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosCadastroTopico dados){
        var topico = topicoRepository.getReferenceById(dados.id());
        topico.atualizarDados(dados);
    }

}
