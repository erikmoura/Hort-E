package com.horte.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para criação de um novo post no blog")
public class PostCreateRequest {

    @NotBlank(message = "O texto do post não pode estar vazio.")
    @Size(max = 1000, message = "O texto do post não pode exceder 1000 caracteres.")
    @Schema(description = "Conteúdo principal do post",
            example = "Hoje plantei algumas sementes de manjericão e estou animado para vê-las crescer!",
            maxLength = 1000)
    private String postTexto;

    @Schema(description = "URL opcional de uma imagem para o post",
            example = "https://example.com/imagens/meu-manjericao.jpg",
            nullable = true)
    private String postImagemUrl;
}