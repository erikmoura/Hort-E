package com.horte.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para requisição de atualização de perfil de usuário. Senha e roles são gerenciadas separadamente.")
public class UsuarioUpdateRequest {
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres se fornecido.")
    @Schema(description = "Novo nome de usuário (opcional)",
            example = "novoNomeDoUsuario",
            minLength = 3,
            maxLength = 50,
            nullable = true)
    private String username;
    @Email(message = "Email deve ser válido se fornecido.")
    @Schema(description = "Novo endereço de e-mail do usuário (opcional)",
            example = "email.atualizado@example.com",
            nullable = true)
    private String usuarioEmail;

    @Schema(description = "Nova URL opcional da imagem de perfil do usuário",
            example = "https://example.com/imagens/perfil/usuarioAtualizado.jpg",
            nullable = true)
    private String usuarioImagemUrl;

    @Schema(description = "Nova localização do usuário (cidade, estado, etc.)",
            example = "Rio de Janeiro, RJ",
            nullable = true)
    private String localizacao;

    @Schema(description = "Novo tipo de horta que o usuário possui ou se interessa (1 para jardim, 2 para interna, 3 para ambos)",
            example = "3",
            allowableValues = {"1", "2", "3"},
            nullable = true)
    private Integer hortaTipo;
}