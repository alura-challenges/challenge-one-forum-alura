package com.br.alura.forum.controller;

import com.br.alura.forum.domain.resposta.DadosDetalhesResposta;
import com.br.alura.forum.domain.resposta.DadosResposta;
import com.br.alura.forum.domain.resposta.Resposta;
import com.br.alura.forum.domain.resposta.RespostaRepository;
import com.br.alura.forum.domain.topico.Topico;
import com.br.alura.forum.domain.topico.TopicoRepository;
import com.br.alura.forum.domain.usuario.Usuario;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/topicos/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository repository;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity inserir(@RequestBody @Valid DadosResposta dados, UriComponentsBuilder builder){
        // busca o topico a ser respondido pelo id
        var topicoRespondido =
                topicoRepository.getReferenceById(dados.topico().getId());


        //Passar qual o tópico que está sendo respondido
        dados.topico().setTitulo(topicoRespondido.getTitulo());
        dados.topico().setMensagem(topicoRespondido.getMensagem());
        dados.topico().setAutor(topicoRespondido.getAutor());
        dados.topico().setCurso(topicoRespondido.getCurso());

        var resposta = new Resposta(dados);

        resposta.setDataCriacao(LocalDateTime.now());
        repository.save(resposta);

        var uri = builder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesResposta(resposta));
    }
}
