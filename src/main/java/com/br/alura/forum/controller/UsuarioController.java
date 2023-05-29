package com.br.alura.forum.controller;

import com.br.alura.forum.domain.usuario.DadosCadastroUsuario;
import com.br.alura.forum.domain.usuario.DadosDetalhesUsuario;
import com.br.alura.forum.domain.usuario.Usuario;
import com.br.alura.forum.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder builder){
        var usuario = new Usuario(dados);

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        repository.save(usuario);
        var uri = builder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesUsuario(usuario));

    }

    @GetMapping
    public ResponseEntity<Page<DadosCadastroUsuario>> listar(@PageableDefault(size = 10) Pageable paginacao){
        var page = repository.findAll(paginacao).map(DadosCadastroUsuario::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosCadastroUsuario dados){
        var usuario = repository.getReferenceById(dados.id());
        usuario.atualizarDados(dados);
        //Não é boa prática devolver uma entidade JPA.
        // Precisa devolver todos os dados do usuario.
        // Para isso, precisa de um DTO específico para isso.
        return ResponseEntity.ok(new DadosDetalhesUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        Optional<Usuario> optional = repository.findById(id);
        if(optional.isPresent()){
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
