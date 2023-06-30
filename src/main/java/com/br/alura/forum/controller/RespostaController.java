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
    public ResponseEntity cadastrar(@RequestBody DadosCadastroResposta dados, UriComponentsBuilder uriBuilder){
        var autor = usuarioRepository.getReferenceById(dados.autor());
        var topico = topicoRepository.getReferenceById(dados.topico());

        if (topico.getStatus() == StatusTopico.FECHADO){
            return ResponseEntity.badRequest().body("Tópico fechado! Não é possível enviar respostas.");
        }

        var resposta = new Resposta(dados, autor, topico);
        respostaRepository.save(resposta);

        if(topico.getStatus() == StatusTopico.NAO_RESPONDIDO){
            topico.setStatus(StatusTopico.NAO_SOLUCIONADO);
        }

        var uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoResposta(resposta));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemRespostas>> listar(
            @RequestParam(required = false) String autorId,
            @RequestParam(required = false) String topicoId,
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = {"dataCriacao"})Pageable paginacao){

        Page<Resposta> paginaRespostas;

        if((autorId != null) && !Pattern.matches("\\d", autorId)) {
            return ResponseEntity.badRequest().build();
        }

        if((topicoId != null) && !Pattern.matches("\\d", topicoId)) {
            return ResponseEntity.badRequest().build();
        }

        if((autorId != null) && (topicoId != null)){
            var buscaAutor = usuarioRepository.getReferenceById(Long.parseLong(autorId));
            var buscaTopico = topicoRepository.getReferenceById(Long.parseLong(topicoId));

            paginaRespostas = respostaRepository.findAllByAtivoTrueAndAutorAndTopico(buscaAutor, buscaTopico, paginacao);
        } else if(autorId != null){
            var buscaAutor = usuarioRepository.getReferenceById(Long.parseLong(autorId));
            paginaRespostas = respostaRepository.findAllByAtivoTrueAndAutor(buscaAutor, paginacao);
        } else if(topicoId != null){
            var buscaTopico = topicoRepository.getReferenceById(Long.parseLong(topicoId));
            paginaRespostas = respostaRepository.findAllByAtivoTrueAndTopico(buscaTopico, paginacao);
        }
        else{
            paginaRespostas = respostaRepository.findAllByAtivoTrue(paginacao);
        }
        return ResponseEntity.ok(paginaRespostas.map(DadosListagemRespostas::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoResposta> detalhar(@PathVariable Long id){
        var resposta = respostaRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody DadosAtualizacaoResposta dados){
        var resposta = respostaRepository.getReferenceById(dados.id());
        if(resposta.getTopico().getStatus() == StatusTopico.FECHADO){
            return ResponseEntity.badRequest().body("Tópico fechado! Não é possível editar a Resposta.");
        }
        resposta.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoResposta(resposta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        var resposta = respostaRepository.getReferenceById(id);
        resposta.excluir();
        return ResponseEntity.noContent().build();
    }


}