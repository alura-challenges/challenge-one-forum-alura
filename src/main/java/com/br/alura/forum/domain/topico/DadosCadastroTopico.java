package com.br.alura.forum.domain.topico;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DadosCadastroTopico(

        Long id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensagem,
        LocalDateTime dataCriacao
) {


        public DadosCadastroTopico(Topico topico) {
                this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao());
        }
}
