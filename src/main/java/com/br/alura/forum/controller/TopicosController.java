package com.br.alura.forum.controller;

import com.br.alura.forum.domain.topico.DadosCadastroTopico;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @PostMapping
    public void cadastrar(@RequestBody DadosCadastroTopico dados){
        System.out.println(dados);
    }

}
