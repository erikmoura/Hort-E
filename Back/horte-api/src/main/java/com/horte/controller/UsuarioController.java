package com.horte.controller;

import com.horte.dto.UsuarioProfileResponse;
import com.horte.dto.UsuarioResponse;
import com.horte.service.UsuarioService;
import com.horte.model.Usuario;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

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
    @GetMapping("/{username}/profile")
    public ResponseEntity<UsuarioProfileResponse> getUsuarioProfileByUsername(@PathVariable String username) {
        return usuarioService.buscarPerfilUsuarioPorUsername(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para buscar informações de perfil de um usuário pelo seu ID.
    @Operation(
        summary = "Buscar perfil de usuário por ID",
        description = "Retorna informações públicas de perfil de um usuário (username, imagem, localização, tipo de horta) a partir do ID. Este endpoint é de acesso público.",
        parameters = @Parameter(name = "id", description = "O ID do usuário cujo perfil será buscado", required = true, example = "1")
    )
    @ApiResponse(responseCode = "200", description = "Perfil do usuário retornado com sucesso",
                 content = @Content(schema = @Schema(implementation = UsuarioProfileResponse.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @GetMapping("/{id}/profile/id")
    public ResponseEntity<UsuarioProfileResponse> getUsuarioProfileById(@PathVariable Long id) {
        return usuarioService.buscarPerfilUsuarioPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    // Endpoints de Administração

    @Operation(
        summary = "Listar todos os usuários (ADMIN ONLY)",
        description = "Retorna uma lista completa de todos os usuários registrados. Requer a role 'ADMIN'.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                 content = @Content(schema = @Schema(type = "array", implementation = UsuarioResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (requer ROLE_ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<List<UsuarioResponse>> getAllUsersAdmin() {
        List<UsuarioResponse> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @Operation(
        summary = "Tornar usuário administrador (ADMIN ONLY)",
        description = "Atribui a role 'ADMIN' a um usuário específico. Requer a role 'ADMIN'.",
        parameters = @Parameter(name = "id", description = "ID do usuário a ser tornado administrador", required = true, example = "2"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Usuário atualizado para administrador com sucesso",
                 content = @Content(schema = @Schema(implementation = UsuarioResponse.class)))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (requer ROLE_ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{id}/make-admin")
    public ResponseEntity<UsuarioResponse> makeUserAdmin(@PathVariable Long id) {
        try {
            UsuarioResponse updatedUser = usuarioService.tornarAdministrador(id);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Atualizar dados do usuário",
        description = "Atualiza informações de perfil de um usuário (username, email, imagem). " +
                      "A senha e as roles (funções) não são modificadas por este endpoint. " +
                      "Requer autenticação como o próprio usuário ou como ADMIN.",
        parameters = {
            @Parameter(name = "id", description = "ID do usuário a ser atualizado", required = true, example = "1")
        },
        requestBody = @RequestBody(
            description = "Objeto JSON com os dados do usuário a serem atualizados.",
            required = true,
            content = @Content(schema = @Schema(implementation = Usuario.class))
        ),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponse.class)))
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (tentando atualizar outro usuário sem ser ADMIN)")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(
            @PathVariable Long id,
            @org.springframework.web.bind.annotation.RequestBody Usuario usuarioAtualizado) {
        
        try {
            UsuarioResponse usuarioSalvo = usuarioService.atualizarUsuario(id, usuarioAtualizado);
            return ResponseEntity.ok(usuarioSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}