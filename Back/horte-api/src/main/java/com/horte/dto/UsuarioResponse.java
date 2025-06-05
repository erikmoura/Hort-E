package com.horte.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set; // Importar Set

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de resposta para detalhes de um usuário, omitindo informações sensíveis como a senha.")
public class UsuarioResponse {

    @Schema(description = "ID único do usuário.", example = "1")
    private Long id;

    @Schema(description = "Nome de usuário.", example = "joaopedro")
    private String username;

    @Schema(description = "Endereço de e-mail do usuário.", example = "joao.pedro@example.com")
    private String usuarioEmail;

    @Schema(description = "Tipo de usuário (e.g., 1 para usuário comum, 2 para admin).", example = "1", nullable = true)
    private Integer usuarioTipo; // Nota: Se este campo se refere a um tipo de usuário que não é a role, ele pode ser mantido. Se for para refletir a role, podemos revisar.

    @Schema(description = "URL da imagem de perfil do usuário.", example = "https://example.com/imagens/perfil/joaopedro.jpg", nullable = true)
    private String usuarioImagemUrl;

    @Schema(description = "Localização do usuário (cidade, estado, etc.).", example = "Niterói, RJ", nullable = true)
    private String localizacao;

    @Schema(description = "Tipo de horta que o usuário possui ou se interessa (e.g., 1 para orgânica, 2 para hidropônica).", example = "1", nullable = true)
    private Integer hortaTipo;

    // NOVO CAMPO: Para retornar as roles do usuário
    @Schema(description = "Roles (papéis de permissão) atribuídas ao usuário, como 'ROLE_USER', 'ROLE_ADMIN'.",
            example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
    private Set<String> roles;
}