package com.horte.controller;

import com.horte.model.Planta;
import com.horte.service.PlantaService;
import com.horte.dto.PlantaCreateRequest;
import com.horte.dto.PlantaResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plantas")
@Tag(name = "Plantas", description = "Endpoints para gerenciamento de informações sobre plantas")
public class PlantaController {

    @Autowired
    private PlantaService plantaService;

    @Operation(
        summary = "Listar todas as plantas",
        description = "Retorna uma lista de todas as plantas cadastradas."
    )
    @ApiResponse(responseCode = "200", description = "Lista de plantas retornada com sucesso",
                 content = @Content(schema = @Schema(implementation = PlantaResponse.class)))
    @GetMapping
    public ResponseEntity<List<PlantaResponse>> getAllPlantas() {
        List<PlantaResponse> plantas = plantaService.listarTodasPlantasDTO();
        return ResponseEntity.ok(plantas);
    }

    @Operation(
        summary = "Buscar planta por ID",
        description = "Retorna os detalhes de uma planta específica.",
        parameters = @Parameter(name = "id", description = "ID da planta a ser buscada", required = true, example = "1")
    )
    @ApiResponse(responseCode = "200", description = "Planta encontrada com sucesso",
                 content = @Content(schema = @Schema(implementation = PlantaResponse.class)))
    @ApiResponse(responseCode = "404", description = "Planta não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<PlantaResponse> getPlantaById(@PathVariable Long id) {
        Optional<PlantaResponse> plantaResponse = plantaService.buscarPlantaPorIdDTO(id);

        return plantaResponse
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

// Endpoint de Filtragem

    @Operation(
        summary = "Listar plantas com filtros opcionais",
        description = "Retorna uma lista de plantas que correspondem aos critérios de filtro fornecidos. " +
                      "Se um filtro for 'todos' (case-insensitive), ele será ignorado. " +
                      "Este endpoint é de acesso público.",
        parameters = {
            @Parameter(name = "categoria", description = "Filtra por categoria da planta.", 
                       example = "Fruta", required = false,
                       schema = @Schema(type = "string", allowableValues = {"todos", "Erva", "Hortaliça", "Fruto", "Raiz", "Bulbo", "Tubérculo", "Leguminosa"})),
            @Parameter(name = "tipoSolo", description = "Filtra por tipo de solo ideal.", 
                       example = "Bem drenado", required = false,
                       schema = @Schema(type = "string", allowableValues = {"todos", "Fértil e Bem Drenado", "Rico em Matéria Orgânica (Húmus)", "Leve e Bem Drenado", "Arenoso e Bem Drenado (Pouco Fértil / Seco)", "Rico e Profundo", "Rico e Levemente Ácido", "Rico em Matéria Orgânica e Bem Drenado", "Bem Drenado (Até Pobre)"})),
            @Parameter(name = "irrigacao", description = "Filtra por necessidades de irrigação.", 
                       example = "Diária", required = false,
                       schema = @Schema(type = "string", allowableValues = {"todos", "diária", "a cada 1-2 dias", "a cada 2-3 dias", "a cada 3-5 dias", "semanal", "a cada 7-15 dias"})),
            @Parameter(name = "localPlantio", description = "Filtra por local de plantio recomendado.", 
                       example = "Vaso", required = false,
                       schema = @Schema(type = "string", allowableValues = {"todos", "Apartamentos e Casas (Sol Pleno)", "Todos os Locais (Ideal em Vasos)", "Varandas e Hortas Elevadas", "Janelas, Varandas", "Vasos ou Jardineiras", "Vasos Grandes e Quintais/Jardins", "Vasos (Local Ensolarado)", "Varandas e Quintais Ensolarados", "Varandas e Jardineiras", "Vasos e Canteiros (Sombreados/Luz Difusa)", "Vasos e Jardineiras", "Quintais e Hortas Amplas", "Varanda, Dentro (Perto de Janela) e Quintal"})),
            @Parameter(name = "clima", description = "Filtra por clima ideal.", 
                       example = "Quente e úmido", required = false,
                       schema = @Schema(type = "string", allowableValues = {"todos", "Mediterrâneo", "Temperado a Subtropical", "Temperado", "Tropical a Subtropical", "Temperado a Quente", "Tropical", "Temperado a Frio", "Tropical a Temperado", "Tropical a Quente"})),
            @Parameter(name = "luzSolar", description = "Filtra por necessidade de luz solar.", 
                       example = "Sol pleno", required = false,
                       schema = @Schema(type = "string", allowableValues = {"todos", "Sol pleno (4-6h)", "Meia-sombra", "Sol pleno ou meia-sombra", "Sol pleno (6-8h)", "Sol pleno (6-7h)", "Sol pleno (4h+) (tolera um pouco de sombra)"}))
        }
    )
    @ApiResponse(responseCode = "200", description = "Lista de plantas filtrada retornada com sucesso",
                 content = @Content(schema = @Schema(implementation = PlantaResponse.class)))
    @GetMapping("/filtrar")
    public ResponseEntity<List<PlantaResponse>> getFilteredPlantas(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String tipoSolo,
            @RequestParam(required = false) String irrigacao,
            @RequestParam(required = false) String localPlantio,
            @RequestParam(required = false) String clima,
            @RequestParam(required = false) String luzSolar) {

        List<PlantaResponse> plantasFiltradas = plantaService.buscarPlantasPorFiltro(
                categoria, tipoSolo, irrigacao, localPlantio, clima, luzSolar);
        return ResponseEntity.ok(plantasFiltradas);
    }

    @Operation(
        summary = "Criar uma nova planta (ADMIN ONLY)",
        description = "Cria uma nova entrada de planta. Requer a role 'ADMIN'.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "201", description = "Planta criada com sucesso",
                 content = @Content(schema = @Schema(implementation = PlantaResponse.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (requer ROLE_ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PlantaResponse> createPlanta(@Valid @RequestBody PlantaCreateRequest plantaCreationDTO) {
        Planta planta = new Planta();
        BeanUtils.copyProperties(plantaCreationDTO, planta);

        Planta novaPlanta = plantaService.criarPlanta(planta);
        return new ResponseEntity<>(PlantaResponse.fromEntity(novaPlanta), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Atualizar uma planta existente (ADMIN ONLY)",
        description = "Atualiza os detalhes de uma planta. Requer a role 'ADMIN'.",
        parameters = @Parameter(name = "id", description = "ID da planta a ser atualizada", required = true, example = "1"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Planta atualizada com sucesso",
                 content = @Content(schema = @Schema(implementation = PlantaResponse.class)))
    @ApiResponse(responseCode = "404", description = "Planta não encontrada")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (requer ROLE_ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PlantaResponse> updatePlanta(@PathVariable Long id, @RequestBody Planta planta) {
        try {
            Planta plantaAtualizada = plantaService.atualizarPlanta(id, planta);
            return ResponseEntity.ok(PlantaResponse.fromEntity(plantaAtualizada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Deletar uma planta (ADMIN ONLY)",
        description = "Remove uma planta. Requer a role 'ADMIN'.",
        parameters = @Parameter(name = "id", description = "ID da planta a ser deletada", required = true, example = "1"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "204", description = "Planta deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Planta não encontrada")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (requer ROLE_ADMIN)")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanta(@PathVariable Long id) {
        try {
            plantaService.deletarPlanta(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}