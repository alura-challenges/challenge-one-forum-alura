package com.br.alura.forum.domain.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String senha;

	public Usuario(DadosCadastroUsuario dados) {
		this.id = dados.id();
		this.nome = dados.nome();
		this.email = dados.email();
		this.senha = dados.senha();
	}


	//para controle de perfis, precisa criar uma classe para isso e usa o metodo abaixo.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void atualizarDados(DadosCadastroUsuario dados) {
		if(dados.nome() != null){
			this.nome = dados.nome();
		}
		if(dados.email() != null){
			this.email = dados.email();
		}
		if(dados.senha() != null){
			this.senha = dados.senha();
		}
	}
}
