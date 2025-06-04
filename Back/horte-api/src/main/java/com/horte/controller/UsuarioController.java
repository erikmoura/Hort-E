package com.horte.controller;

import com.horte.dto.UsuarioProfileResponse;
import com.horte.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// Controlador REST para gerenciar informações e operações relacionadas a usuários.

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de informações de perfil de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

// Endpoint para buscar informações de perfil de um usuário pelo seu username.
    @Operation(
        summary = "Buscar perfil de usuário por username",
        description = "Retorna informações públicas de perfil de um usuário (username, imagem, localização, tipo de horta). Este endpoint é de acesso público.",
        parameters = @Parameter(name = "username", description = "O username do usuário cujo perfil será buscado", required = true, example = "floricultor_master")
    )
    @ApiResponse(responseCode = "200", description = "Perfil do usuário retornado com sucesso",
                 content = @Content(schema = @Schema(implementation = UsuarioProfileResponse.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @GetMapping("/{username}/profile") // Exemplo de URL seria /api/usuarios/joao_silva/profile
    public ResponseEntity<UsuarioProfileResponse> getUsuarioProfileByUsername(@PathVariable String username) {
        return usuarioService.buscarPerfilUsuarioPorUsername(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}