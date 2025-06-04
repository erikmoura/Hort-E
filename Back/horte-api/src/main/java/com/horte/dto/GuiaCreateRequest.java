package com.horte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para a criação de um novo guia de planta. Inclui apenas os campos de entrada necessários.")
public class GuiaCreateRequest {
    @Size(max = 255, message = "A URL da imagem do guia não pode exceder 255 caracteres.")
    @Schema(description = "URL opcional para uma imagem que representa o guia.",
            example = "https://example.com/imagens/guia-cultivo-tomate.jpg",
            nullable = true)
    private String guiaImagemUrl;

    @NotBlank(message = "O título do guia é obrigatório.")
    @Size(max = 255, message = "O título do guia não pode exceder 255 caracteres.")
    @Schema(description = "O título do guia.", example = "Guia Completo para Cultivo de Tomates Cereja", required = true)
    private String guiaTitulo;

    @NotBlank(message = "O conteúdo do guia é obrigatório.")
    @Schema(description = "O conteúdo principal e detalhado do guia.",
            example = "Para cultivar tomates cereja em casa, você precisará de um local com bastante luz solar...", required = true)
    private String guiaConteudo;

    @Schema(description = "Tipo de ambiente da horta que o guia explica (1-ambos, 2-interior, 3-jardim).",
            example = "1",
            allowableValues = {"1", "2", "3"},
            nullable = true)
    private Integer guiaTipo;
}