package com.br.alura.forum.controller;

import com.br.alura.forum.domain.resposta.*;
import com.br.alura.forum.domain.topico.TopicoRepository;
import com.br.alura.forum.domain.topico.TopicoService;
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
import java.util.Optional;

@RestController
@RequestMapping("/topicos/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;
    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @Transactional
    public ResponseEntity inserir(@RequestBody @Valid DadosResposta dados, UriComponentsBuilder builder) {
        topicoService.validaDados(dados);

        var resposta = new Resposta(dados);
        resposta.setDataCriacao(LocalDateTime.now());
        respostaRepository.save(resposta);

        var uri = builder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesResposta(resposta));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhesResposta>> listar(@PageableDefault(size = 10, sort = {"autor_id"}) Pageable paginacao) {
        var page = respostaRepository.findAll(paginacao).map(DadosDetalhesResposta::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizaResposta dados) {
        var resposta = respostaRepository.getReferenceById(dados.id());
        resposta.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhesResposta(resposta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        Optional<Resposta> optional = respostaRepository.findById(id);
        if (optional.isPresent()) {
            respostaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
