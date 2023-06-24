package com.br.alura.forum.controller;

import com.br.alura.forum.domain.resposta.*;
import com.br.alura.forum.domain.topico.StatusTopico;
import com.br.alura.forum.domain.topico.TopicoRepository;
import com.br.alura.forum.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoResposta> cadastrar(@RequestBody DadosCadastroResposta dados, UriComponentsBuilder uriBuilder){
        var autor = usuarioRepository.getReferenceById(dados.autor());
        var topico = topicoRepository.getReferenceById(dados.topico());
        var resposta = new Resposta(dados, autor, topico);
        respostaRepository.save(resposta);

        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

    /*@GetMapping
    public ResponseEntity<Page<DadosListagemRespostas>> listar(
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String topico,
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = {"dataCriacao"})Pageable paginacao){
        Long autorId;
        Long topicoId;
        Page<Resposta> paginaRespostas;

        if((autor != null) && !Pattern.matches("\\d", autor)){
            return ResponseEntity.badRequest().build();
        }
        autorId = Long.parseLong(autor);
        if((topico != null) && Pattern.matches("\\d", topico)){
            return ResponseEntity.badRequest().build();
        }
        topicoId = Long.parseLong(topico);

        if((autor != null) && (topico != null)){
            var autor = usuarioRepository.getReferenceById(autorId);

            paginaRespostas = respostaRepository.
        }


        var respostas = respostaRepository.findAllByAtivoTrue(paginacao);
        return ResponseEntity.ok(respostas.map(DadosListagemRespostas::new));
    }*/
}
