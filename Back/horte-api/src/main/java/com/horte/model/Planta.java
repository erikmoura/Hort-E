package com.horte.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "plantas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"guias"})
@EqualsAndHashCode(exclude = {"guias"})
@Schema(description = "Representa uma planta com suas características e requisitos de cultivo.")
public class Planta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "planta_id")
    @Schema(description = "Identificador único da planta.", example = "1")
    private Long id;

    @Column(name = "nome_comum", nullable = false)
    @Schema(description = "O nome comum da planta.", example = "Tomate Cereja")
    private String nomeComum;

    @Column(name = "nome_cientifico", unique = true)
    @Schema(description = "O nome científico da planta (único).", example = "Solanum lycopersicum var. cerasiforme", nullable = true)
    private String nomeCientifico;

    @Column(name = "categoria")
    @Schema(description = "A categoria da planta (ex: Fruta, Erva, Flor, Vegetal).", example = "Fruta")
    private String categoria;

    @Column(name = "planta_imagem_url")
    @Schema(description = "URL opcional para uma imagem da planta.",
            example = "https://example.com/imagens/tomate-cereja.jpg",
            nullable = true)
    private String plantaImagemUrl;

    @Column(name = "descricao", columnDefinition = "TEXT")
    @Schema(description = "Uma descrição detalhada sobre a planta.", example = "O tomate cereja é uma variedade pequena e doce de tomate, ideal para saladas e lanches.")
    private String descricao;

    @Column(name = "tipo_solo")
    @Schema(description = "O tipo de solo ideal para o cultivo da planta (ex: Argiloso, Arenoso, Rico em Matéria Orgânica).", example = "Bem drenado, rico em matéria orgânica")
    private String tipoSolo;

    @Column(name = "irrigacao")
    @Schema(description = "As necessidades de irrigação da planta (ex: Diária, Semanal, Moderada).", example = "Regas regulares, solo úmido mas não encharcado")
    private String irrigacao;

    @Column(name = "local_plantio")
    @Schema(description = "O local de plantio recomendado (ex: Vaso, Horta, Jardim, Interior).", example = "Vaso ou canteiro externo")
    private String localPlantio;

    @Column(name = "clima")
    @Schema(description = "O clima ideal para o cultivo da planta (ex: Tropical, Temperado, Subtropical).", example = "Quente e úmido")
    private String clima;

    @Column(name = "luz_solar")
    @Schema(description = "A necessidade de luz solar da planta (ex: Sol Pleno, Meia Sombra, Sombra).", example = "Sol pleno (mínimo de 6 horas de sol direto)")
    private String luzSolar;

    @ManyToMany(mappedBy = "plantasAssociadas")
    @Schema(description = "Conjunto de guias associados a esta planta.")
    @JsonBackReference("guia-plantas")
    private Set<Guia> guias = new HashSet<>();
}