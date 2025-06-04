package com.horte.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para requisição de registro de um novo usuário")
public class UsuarioRegisterRequest {

    @NotBlank(message = "Username é obrigatório.")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres.")
    @Schema(description = "Nome de usuário único para registro",
            example = "novoUsuario123",
            minLength = 3,
            maxLength = 50)
    private String username;

    @NotBlank(message = "Senha é obrigatório.")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres.")
    @Schema(description = "Senha para o novo usuário",
            example = "MinhaSenhaForte123",
            minLength = 6)
    private String senha;

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email deve ser válido.")
    @Schema(description = "Endereço de e-mail do usuário",
            example = "novo.usuario@example.com")
    private String usuarioEmail;

    @Schema(description = "URL opcional da imagem de perfil do usuário",
            example = "https://example.com/imagens/perfil/novoUsuario.jpg",
            nullable = true)
    private String usuarioImagemUrl;

    @Schema(description = "Localização do usuário (cidade, estado, etc.)",
            example = "Niterói, RJ",
            nullable = true)
    private String localizacao;

    @Schema(description = "Tipo de horta que o usuário possui ou se interessa (1 para jardim, 2 para interna, 3 para)",
            example = "1",
            nullable = true)
    private Integer hortaTipo;
}