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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"autor", "comentarios"})
@EqualsAndHashCode(exclude = {"autor", "comentarios"})
@Schema(description = "Representa uma publicação (post) feita por um usuário na plataforma.")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    @Schema(description = "Identificador único do post.", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @Schema(description = "O usuário que é o autor deste post.")
    @JsonBackReference("usuario-posts")
    private Usuario autor;

    @Column(name = "post_texto", nullable = false, columnDefinition = "TEXT")
    @Schema(description = "O conteúdo textual principal do post.", example = "Compartilhando meu progresso com a nova horta de temperos! Crescendo muito bem.")
    private String postTexto;

    @Column(name = "post_data", nullable = false)
    @Schema(description = "A data em que o post foi criado.", example = "2024-05-30")
    private LocalDate postData;

    @Column(name = "post_imagem_url")
    @Schema(description = "URL opcional para uma imagem associada ao post.",
            example = "https://example.com/imagens/minha-horta.jpg",
            nullable = true)
    private String postImagemUrl;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de comentários associados a este post.")
    @JsonManagedReference("post-comentarios")
    private List<Comentario> comentarios = new ArrayList<>();
}