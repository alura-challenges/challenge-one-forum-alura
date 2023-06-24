package com.br.alura.forum.domain.resposta;

import java.time.LocalDateTime;

public record DadosCadastroResposta(
        String mensagem,
        Long topico,
        Long autor

) {
}
