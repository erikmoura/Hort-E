package com.horte.controller;

import com.horte.model.Guia;
import com.horte.model.Usuario;
import com.horte.service.GuiaService;
import com.horte.service.UsuarioService;
import com.horte.dto.GuiaCreateRequest;
import com.horte.dto.GuiaResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

// Controlador REST para guia de plantas.

@RestController
@RequestMapping("/api/guias")
@Tag(name = "Guias", description = "Endpoints para gerenciamento de guias de plantas")
public class GuiaController {

    @Autowired
    private GuiaService guiaService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
        summary = "Listar todos os guias",
        description = "Retorna uma lista de todos os guias de plantas disponíveis."
    )
    @ApiResponse(responseCode = "200", description = "Lista de guias retornada com sucesso",
                 content = @Content(schema = @Schema(implementation = GuiaResponse.class)))
    @GetMapping
    public ResponseEntity<List<GuiaResponse>> getAllGuias() {
        List<Guia> guias = guiaService.listarTodosGuias();
        // Converte a lista de entidades Guia para uma lista de DTOs GuiaResponse.
        List<GuiaResponse> guiasDTO = guias.stream()
                                             .map(GuiaResponse::fromEntity)
                                             .collect(Collectors.toList());
        return ResponseEntity.ok(guiasDTO);
    }

    @Operation(
        summary = "Buscar guia por ID",
        description = "Retorna um guia de planta específico dado seu ID.",
        parameters = @Parameter(name = "id", description = "ID do guia a ser buscado", required = true, example = "1")
    )
    @ApiResponse(responseCode = "200", description = "Guia encontrado com sucesso",
                 content = @Content(schema = @Schema(implementation = GuiaResponse.class)))
    @ApiResponse(responseCode = "404", description = "Guia não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<GuiaResponse> getGuiaById(@PathVariable Long id) {
        Optional<Guia> guia = guiaService.buscarGuiaPorId(id);
        return guia.map(GuiaResponse::fromEntity)
                   .map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Criar um novo guia",
        description = "Cria um novo guia de planta. Requer que o usuário esteja autenticado (ROLE_USER ou ROLE_ADMIN).",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "201", description = "Guia criado com sucesso",
                 content = @Content(schema = @Schema(implementation = GuiaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (requer ROLE_USER ou ROLE_ADMIN)")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<GuiaResponse> createGuia(@Valid @RequestBody GuiaCreateRequest guiaCreationDTO, @AuthenticationPrincipal UserDetails currentUser) {
        Guia guia = new Guia();
        BeanUtils.copyProperties(guiaCreationDTO, guia);

        Usuario autor = usuarioService.buscarPorUsername(currentUser.getUsername())
                                             .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não encontrado."));
        Guia novoGuia = guiaService.criarGuia(guia, autor.getId());
        return new ResponseEntity<>(GuiaResponse.fromEntity(novoGuia), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Atualizar um guia existente",
        description = "Atualiza um guia de planta existente. Apenas o autor do guia ou um ADMIN pode realizar esta operação.",
        parameters = @Parameter(name = "id", description = "ID do guia a ser atualizado", required = true, example = "1"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Guia atualizado com sucesso",
                 content = @Content(schema = @Schema(implementation = GuiaResponse.class)))
    @ApiResponse(responseCode = "404", description = "Guia não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (não é o autor ou não tem ROLE_ADMIN)")
    // Verifica se o usuário tem a role 'ADMIN' OU se o username do autor do guia é o mesmo username do usuário autenticado.
    @PreAuthorize("hasRole('ADMIN') or @guiaService.buscarGuiaPorId(#id).orElse(null)?.autor?.username == authentication.name")
    @PutMapping("/{id}")
    public ResponseEntity<GuiaResponse> updateGuia(@PathVariable Long id, @RequestBody Guia guia) {
        try {
            Guia guiaAtualizado = guiaService.atualizarGuia(id, guia);
            return ResponseEntity.ok(GuiaResponse.fromEntity(guiaAtualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Deletar um guia",
        description = "Remove um guia de planta do sistema. Apenas o autor do guia ou um ADMIN pode realizar esta operação.",
        parameters = @Parameter(name = "id", description = "ID do guia a ser deletado", required = true, example = "1"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "204", description = "Guia deletado com sucesso (sem conteúdo)")
    @ApiResponse(responseCode = "404", description = "Guia não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (não é o autor ou não tem ROLE_ADMIN)")
    @PreAuthorize("hasRole('ADMIN') or @guiaService.buscarGuiaPorId(#id).orElse(null)?.autor?.username == authentication.name")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuia(@PathVariable Long id) {
        try {
            guiaService.deletarGuia(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Associar planta a um guia (ADMIN ou autor do guia)",
        description = "Associa uma planta existente a um guia existente. Requer que o usuário seja ADMIN ou o autor do guia.",
        parameters = {
            @Parameter(name = "guiaId", description = "ID do guia", required = true, example = "1"),
            @Parameter(name = "plantaId", description = "ID da planta a ser associada", required = true, example = "10")
        },
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Planta associada com sucesso",
                 content = @Content(schema = @Schema(implementation = GuiaResponse.class)))
    @ApiResponse(responseCode = "404", description = "Guia ou Planta não encontrados")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (não é ADMIN nem o autor do guia)")
    @PreAuthorize("hasRole('ADMIN') or @guiaService.buscarGuiaPorId(#guiaId).orElse(null)?.autor?.username == authentication.name")
    @PostMapping("/{guiaId}/associar-planta/{plantaId}")
    public ResponseEntity<GuiaResponse> associarPlanta(@PathVariable Long guiaId, @PathVariable Long plantaId) {
        try {
            Guia guiaAtualizado = guiaService.associarPlantaAoGuia(guiaId, plantaId);
            return ResponseEntity.ok(GuiaResponse.fromEntity(guiaAtualizado));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Desassociar planta de um guia (ADMIN ou autor do guia)",
        description = "Remove a associação de uma planta a um guia. Requer que o usuário seja ADMIN ou o autor do guia.",
        parameters = {
            @Parameter(name = "guiaId", description = "ID do guia", required = true, example = "1"),
            @Parameter(name = "plantaId", description = "ID da planta a ser desassociada", required = true, example = "10")
        },
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Planta desassociada com sucesso",
                 content = @Content(schema = @Schema(implementation = GuiaResponse.class)))
    @ApiResponse(responseCode = "404", description = "Guia ou Planta não encontrados")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (não é ADMIN nem o autor do guia)")
    @PreAuthorize("hasRole('ADMIN') or @guiaService.buscarGuiaPorId(#guiaId).orElse(null)?.autor?.username == authentication.name")
    @DeleteMapping("/{guiaId}/desassociar-planta/{plantaId}")
    public ResponseEntity<GuiaResponse> desassociarPlanta(@PathVariable Long guiaId, @PathVariable Long plantaId) {
        try {
            Guia guiaAtualizado = guiaService.desassociarPlantaDoGuia(guiaId, plantaId);
            return ResponseEntity.ok(GuiaResponse.fromEntity(guiaAtualizado));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}