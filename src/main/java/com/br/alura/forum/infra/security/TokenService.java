package com.br.alura.forum.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.br.alura.forum.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    public String geraToken(Usuario usuario){
        try {

            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Fórum da Alura") // identifica de quem é a api
                    .withSubject(usuario.getEmail()) // identifica o usuario passado no parametro do metodo
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritimo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar o token jwt", exception);
        }
    }

    public String getSubject (String tokenJwt){
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("Fórum da Alura")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();

        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido ou expirado!", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now()
                .plusHours(2) // quantidade de tempo para expiração
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
