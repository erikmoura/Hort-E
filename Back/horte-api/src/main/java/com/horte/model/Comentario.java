package com.horte.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "comentarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"post", "autor"})
@EqualsAndHashCode(exclude = {"post", "autor"})
@Schema(description = "Representa um comentário feito por um usuário em um post específico.")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comentario_id")
    @Schema(description = "Identificador único do comentário.", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @Schema(description = "O post ao qual este comentário pertence.")
    @JsonBackReference("post-comentarios")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "O usuário que criou este comentário.")
    @JsonBackReference("usuario-comentarios")
    private Usuario autor;

    @Column(name = "comentario_data", nullable = false)
    @Schema(description = "Data em que o comentário foi criado.", example = "2024-05-30")
    private LocalDate comentarioData;

    @Column(name = "comentario_texto", nullable = false, columnDefinition = "TEXT")
    @Schema(description = "O conteúdo textual do comentário.", example = "Concordo plenamente! Minhas plantas também se beneficiam muito da luz solar da manhã.")
    private String comentarioTexto;
}