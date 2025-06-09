package com.horte.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.List;
import com.horte.model.Comentario;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de resposta para detalhes de um post.")
public class PostResponse {

    @Schema(description = "ID único do post.", example = "1")
    private Long id;

    @Schema(description = "Usuário autor do post.")
    private UsuarioResponse autor;

    @Schema(description = "Texto principal do post.", example = "Compartilhando meu progresso com a nova horta de temperos! Crescendo muito bem.")
    private String postTexto;

    @Schema(description = "Data em que o post foi criado.", example = "2024-05-30")
    private String postData;

    @Schema(description = "URL opcional para uma imagem associada ao post.", example = "https://example.com/imagens/minha-horta.jpg", nullable = true)
    private String postImagemUrl;

    @Schema(description = "Lista de comentários associados ao post.")
    private List<Comentario> comentarios;

    
}
