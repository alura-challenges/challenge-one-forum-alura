package com.br.alura.forum.controller;

import com.br.alura.forum.domain.resposta.DadosDetalhesResposta;
import com.br.alura.forum.domain.resposta.DadosResposta;
import com.br.alura.forum.domain.resposta.Resposta;
import com.br.alura.forum.domain.resposta.RespostaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity inserir(@RequestBody @Valid DadosResposta dados, UriComponentsBuilder builder){
        var resposta = new Resposta(dados);
//        var topico = dados.topico();
//        var usuario = dados.autor();
//        resposta.setTopico(topico);
//        resposta.setAutor(usuario);

        resposta.setDataCriacao(LocalDateTime.now());
        repository.save(resposta);
        var uri = builder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesResposta(resposta));
    }
}
