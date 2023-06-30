package com.br.alura.forum.controller;

import com.br.alura.forum.domain.curso.CursoRepository;
import com.br.alura.forum.domain.topico.*;
import com.br.alura.forum.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroTopico dados, UriComponentsBuilder uriBuilder){

        if(topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem()) == null){

            var autor = usuarioRepository.getReferenceById(dados.autor());

            var curso = cursoRepository.findByNomeAndCategoria(dados.curso().nome(), dados.curso().categoria());

            var topico = new Topico(dados, autor);
            topico.setAutor(autor);
            topico.setCurso(curso);
            topicoRepository.save(topico);

            var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

            return ResponseEntity.created(uri).body(new DadosDetalhamentoTopico(topico));
        }
        return ResponseEntity.badRequest().body("Tópicos duplicados!");
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicos>> listar(@RequestParam(required = false) String nomeCurso,
                                                             @RequestParam(required = false) String anoCriacao,
                                                             @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC, sort = {"dataCriacao"}) Pageable paginacao) {
        Page<Topico> paginaTopicos;
        int year = 0;

        if(anoCriacao != null){
            if(!Pattern.matches("\\d{4}", anoCriacao)){
                return ResponseEntity.badRequest().build();
            }
            year = Integer.parseInt(anoCriacao);
        }
        LocalDateTime dataInicio = LocalDateTime.of(year, Month.JANUARY, 1, 0, 0);
        LocalDateTime dataFim = LocalDateTime.of(year, Month.DECEMBER, 31, 0,0);

        if((nomeCurso != null) && (anoCriacao != null)){
            paginaTopicos = topicoRepository.findAllByAtivoTrueAndCursoNomeAndDataCriacaoBetween(nomeCurso, dataInicio, dataFim, paginacao);
        }
        else if(nomeCurso != null){
            paginaTopicos = topicoRepository.findAllByAtivoTrueAndCursoNome(nomeCurso, paginacao);
        }
        else if(anoCriacao != null){
            paginaTopicos = topicoRepository.findAllByAtivoTrueAndDataCriacaoBetween(dataInicio, dataFim, paginacao);
        }

        else{
            paginaTopicos = topicoRepository.findAllByAtivoTrue(paginacao);
        }
        return ResponseEntity.ok(paginaTopicos.map(DadosListagemTopicos::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody DadosAtualizacaoTopico dados){
        var topico = topicoRepository.getReferenceById(dados.id());
        if(topico.getStatus() == StatusTopico.FECHADO){
            return ResponseEntity.badRequest().body("Tópico fechado! Não é possível editar o Tópico.");
        }

        if((topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem()) == null) ||
        (topicoRepository.findByTituloAndMensagem(dados.titulo(), dados.mensagem())) == topico){
            topico.atualiza(dados);
            return ResponseEntity.ok().body(new DadosDetalhamentoTopico(topico));
        }
        return ResponseEntity.badRequest().body("Tópicos replicados!");
    }

    @PutMapping("/{id}/resolver")
    @Transactional
    public ResponseEntity resolverTopico(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        topico.resolver();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/fechar")
    @Transactional
    public ResponseEntity fecharTopico(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        topico.fechar();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/responder")
    @Transactional
    public ResponseEntity responderTopico(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        topico.responder();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        var topico = topicoRepository.getReferenceById(id);
        topico.exclui();
        return ResponseEntity.noContent().build();
    }

}
