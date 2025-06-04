package com.horte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para a criação de uma nova planta. Inclui apenas os campos de entrada necessários.")
public class PlantaCreateRequest {

    @NotBlank(message = "O nome comum da planta é obrigatório.")
    @Size(max = 255, message = "O nome comum não pode exceder 255 caracteres.")
    @Schema(description = "O nome comum da planta.", example = "Tomate Cereja", required = true)
    private String nomeComum;

    @Size(max = 255, message = "O nome científico não pode exceder 255 caracteres.")
    @Schema(description = "O nome científico da planta (opcional e único).", example = "Solanum lycopersicum var. cerasiforme", nullable = true)
    private String nomeCientifico;

    @Size(max = 255, message = "A categoria não pode exceder 255 caracteres.")
    @Schema(description = "A categoria da planta (ex: Fruta, Erva, Flor, Vegetal).", example = "Fruta", nullable = true)
    private String categoria;

    @Size(max = 255, message = "A URL da imagem não pode exceder 255 caracteres.")
    @Schema(description = "URL opcional para uma imagem da planta.", example = "https://example.com/imagens/tomate-cereja.jpg", nullable = true)
    private String plantaImagemUrl;

    @Schema(description = "Uma descrição detalhada sobre a planta.", example = "O tomate cereja é uma variedade pequena e doce de tomate, ideal para saladas e lanches.", nullable = true)
    private String descricao;

    @Size(max = 255, message = "O tipo de solo não pode exceder 255 caracteres.")
    @Schema(description = "O tipo de solo ideal para o cultivo da planta (ex: Argiloso, Arenoso, Rico em Matéria Orgânica).", example = "Bem drenado, rico em matéria orgânica", nullable = true)
    private String tipoSolo;

    @Size(max = 255, message = "A irrigação não pode exceder 255 caracteres.")
    @Schema(description = "As necessidades de irrigação da planta (ex: Diária, Semanal, Moderada).", example = "Regas regulares, solo úmido mas não encharcado", nullable = true)
    private String irrigacao;

    @Size(max = 255, message = "O local de plantio não pode exceder 255 caracteres.")
    @Schema(description = "O local de plantio recomendado (ex: Vaso, Horta, Jardim, Interior).", example = "Vaso ou canteiro externo", nullable = true)
    private String localPlantio;

    @Size(max = 255, message = "O clima não pode exceder 255 caracteres.")
    @Schema(description = "O clima ideal para o cultivo da planta (ex: Tropical, Temperado, Subtropical).", example = "Quente e úmido", nullable = true)
    private String clima;

    @Size(max = 255, message = "A luz solar não pode exceder 255 caracteres.")
    @Schema(description = "A necessidade de luz solar da planta (ex: Sol Pleno, Meia Sombra, Sombra).", example = "Sol pleno (mínimo de 6 horas de sol direto)", nullable = true)
    private String luzSolar;
}