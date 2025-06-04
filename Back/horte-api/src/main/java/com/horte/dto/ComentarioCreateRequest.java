package com.horte.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para criação de um novo comentário")
public class ComentarioCreateRequest {

    @NotBlank(message = "O texto do comentário não pode estar vazio.")
    @Size(max = 500, message = "O texto do comentário não pode exceder 500 caracteres.")
    @Schema(description = "Conteúdo do comentário", example = "Excelente post! Muito informativo.", maxLength = 500)
    private String comentarioTexto;
}