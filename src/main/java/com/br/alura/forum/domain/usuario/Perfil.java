package com.br.alura.forum.domain.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity(name = "Perfil")
@Table(name = "perfil")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Perfil implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Perfil(String nome){
        this.nome = nome;
    }

    @Override
    public String getAuthority() {
        return nome;
    }
}
