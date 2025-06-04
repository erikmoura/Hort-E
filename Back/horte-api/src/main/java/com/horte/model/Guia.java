package com.horte.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "guias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"autor", "plantasAssociadas"})
@EqualsAndHashCode(exclude = {"autor", "plantasAssociadas"})
@Schema(description = "Representa um guia detalhado sobre o cultivo ou cuidado de plantas, criado por um usuário.")
public class Guia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guia_id")
    @Schema(description = "Identificador único do guia.", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "O usuário que é o autor deste guia.")
    @JsonBackReference("usuario-guias")
    private Usuario autor;

    @Column(name = "guia_imagem_url")
    @Schema(description = "URL opcional para uma imagem que representa o guia.",
            example = "https://example.com/imagens/guia-cultivo-tomate.jpg",
            nullable = true)
    private String guiaImagemUrl;

    @Column(name = "guia_titulo", nullable = false)
    @Schema(description = "O título do guia.", example = "Guia Completo para Cultivo de Tomates Cereja")
    private String guiaTitulo;

    @Column(name = "guia_conteudo", nullable = false, columnDefinition = "TEXT")
    @Schema(description = "O conteúdo principal e detalhado do guia.",
            example = "Para cultivar tomates cereja em casa, você precisará de um local com bastante luz solar...")
    private String guiaConteudo;

    @Column(name = "guia_tipo")
    @Schema(description = "Tipo de ambiente da horta que o guia explica (3-jardim ou 2-interior ou 1-ambos)",
            example = "1",
            allowableValues = {"1", "2", "3"},
            nullable = true)
    private Integer guiaTipo;

    // Relacionamento Many-to-Many com Planta
    @ManyToMany
    @JoinTable(
        name = "guiasxplantas",
        joinColumns = @JoinColumn(name = "guia_id"),
        inverseJoinColumns = @JoinColumn(name = "planta_id")
    )
    @Schema(description = "Conjunto de plantas associadas a este guia.")
    @JsonManagedReference("guia-plantas")
    private Set<Planta> plantasAssociadas = new HashSet<>();
}