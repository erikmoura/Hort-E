package com.horte.dto;

import com.horte.model.Planta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para representar uma planta em respostas da API.")
public class PlantaResponse {

    @Schema(description = "Identificador único da planta.", example = "1")
    private Long id;

    @Schema(description = "O nome comum da planta.", example = "Tomate Cereja")
    private String nomeComum;

    @Schema(description = "O nome científico da planta.", example = "Solanum lycopersicum var. cerasiforme", nullable = true)
    private String nomeCientifico;

    @Schema(description = "A categoria da planta.", example = "Fruta")
    private String categoria;

    @Schema(description = "URL da imagem da planta.", example = "https://example.com/imagens/tomate-cereja.jpg", nullable = true)
    private String plantaImagemUrl;

    @Schema(description = "Descrição detalhada da planta.", example = "O tomate cereja...")
    private String descricao;

    @Schema(description = "Tipo de solo ideal.", example = "Bem drenado")
    private String tipoSolo;

    @Schema(description = "Necessidades de irrigação.", example = "Regas regulares")
    private String irrigacao;

    @Schema(description = "Local de plantio recomendado.", example = "Vaso")
    private String localPlantio;

    @Schema(description = "Clima ideal.", example = "Quente e úmido")
    private String clima;

    @Schema(description = "Necessidade de luz solar.", example = "Sol pleno")
    private String luzSolar;

    public static PlantaResponse fromEntity(Planta planta) {
        PlantaResponse dto = new PlantaResponse();
        dto.setId(planta.getId());
        dto.setNomeComum(planta.getNomeComum());
        dto.setNomeCientifico(planta.getNomeCientifico());
        dto.setCategoria(planta.getCategoria());
        dto.setPlantaImagemUrl(planta.getPlantaImagemUrl());
        dto.setDescricao(planta.getDescricao());
        dto.setTipoSolo(planta.getTipoSolo());
        dto.setIrrigacao(planta.getIrrigacao());
        dto.setLocalPlantio(planta.getLocalPlantio());
        dto.setClima(planta.getClima());
        dto.setLuzSolar(planta.getLuzSolar());
        return dto;
    }
}