package com.horte.dto;

import com.horte.model.Guia;
import com.horte.model.Planta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar um guia de planta em respostas da API, evitando referências circulares.")
public class GuiaResponse {

    @Schema(description = "Identificador único do guia.", example = "1")
    private Long id;

    @Schema(description = "ID do usuário que é o autor deste guia.", example = "101")
    private Long autorId;

    @Schema(description = "Nome de usuário do autor deste guia.", example = "floricultura_urbana")
    private String autorUsername;

    @Schema(description = "URL opcional para uma imagem que representa o guia.",
            example = "https://example.com/imagens/guia-cultivo-tomate.jpg",
            nullable = true)
    private String guiaImagemUrl;

    @Schema(description = "O título do guia.", example = "Guia Completo para Cultivo de Tomates Cereja")
    private String guiaTitulo;

    @Schema(description = "O conteúdo principal e detalhado do guia.",
            example = "Para cultivar tomates cereja em casa, você precisará de um local com bastante luz solar...")
    private String guiaConteudo;

    @Schema(description = "Tipo de ambiente da horta ao qual o guia se refere.", example = "1")
    private Integer guiaTipo;

    @Schema(description = "Conjunto de IDs das plantas associadas a este guia.", example = "[1, 3, 5]")
    private Set<Long> plantasAssociadasIds;

    public static GuiaResponse fromEntity(Guia guia) {
        GuiaResponse dto = new GuiaResponse();
        dto.setId(guia.getId());

        if (guia.getAutor() != null) {
            dto.setAutorId(guia.getAutor().getId());
            dto.setAutorUsername(guia.getAutor().getUsername());
        }

        dto.setGuiaImagemUrl(guia.getGuiaImagemUrl());
        dto.setGuiaTitulo(guia.getGuiaTitulo());
        dto.setGuiaConteudo(guia.getGuiaConteudo());
        dto.setGuiaTipo(guia.getGuiaTipo());

        if (guia.getPlantasAssociadas() != null) {
            dto.setPlantasAssociadasIds(
                guia.getPlantasAssociadas().stream()
                    .map(Planta::getId)
                    .collect(Collectors.toSet())
            );
        }
        return dto;
    }
}