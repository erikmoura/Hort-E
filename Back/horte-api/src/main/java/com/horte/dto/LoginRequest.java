package com.horte.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para requisição de login de usuário")
public class LoginRequest {

    @NotBlank(message = "Username não pode estar vazio.")
    @Size(min = 3, max = 50, message = "Username deve ter entre 3 e 50 caracteres.")
    @Schema(description = "Nome de usuário para login", example = "joaopedro", minLength = 3, maxLength = 50)
    private String username;

    @NotBlank(message = "Senha não pode estar vazia.")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres.")
    @Schema(description = "Senha do usuário para login", example = "senha@123", minLength = 6)
    private String password;
}