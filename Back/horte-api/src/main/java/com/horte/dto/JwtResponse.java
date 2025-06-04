package com.horte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Resposta de autenticação JWT, contendo o token e informações do usuário logado.")
public class JwtResponse {

    @Schema(description = "O token JWT para autenticação em requisições futuras.",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvZSBKb2huIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String token;

    @Schema(description = "Tipo do token, geralmente 'Bearer'.",
            example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "ID único do usuário autenticado.",
            example = "1")
    private Long id;

    @Schema(description = "Nome de usuário do usuário autenticado.",
            example = "joaopedro")
    private String username;

    @Schema(description = "Endereço de e-mail do usuário autenticado.",
            example = "joao.pedro@example.com")
    private String email;
}