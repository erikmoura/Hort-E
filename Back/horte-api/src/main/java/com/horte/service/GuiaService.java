package com.horte.service;

import com.horte.model.Usuario;
import com.horte.model.Guia;
import com.horte.model.Planta;
import com.horte.dto.GuiaResponse;

import com.horte.repository.UsuarioRepository;
import com.horte.repository.GuiaRepository;
import com.horte.repository.PlantaRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GuiaService {

    @Autowired
    private GuiaRepository guiaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PlantaRepository plantaRepository;

    public List<GuiaResponse> listarTodosGuiasDTO() {
        return guiaRepository.findAll().stream()
                .map(GuiaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<GuiaResponse> buscarGuiaPorIdDTO(Long id) {
        return guiaRepository.findById(id)
                .map(GuiaResponse::fromEntity);
    }

    public List<GuiaResponse> buscarGuiasPorFiltro(String guiaTipo) {
        Specification<Guia> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        // Filtro para guia_tipo
        if (StringUtils.hasText(guiaTipo) && !"todos".equalsIgnoreCase(guiaTipo)) {
            try {
                Integer tipoValue = Integer.parseInt(guiaTipo);
                spec = spec.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("guiaTipo"), tipoValue));
            } catch (NumberFormatException e) {
                System.err.println("Valor inválido para guiaTipo: " + guiaTipo + ". Ignorando filtro.");
            }
        }
        // Futuros filtrosno padrão
        // if (StringUtils.hasText(outroFiltro) && !"todos".equalsIgnoreCase(outroFiltro)) { ... }

        return guiaRepository.findAll(spec).stream()
                .map(GuiaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Guia criarGuia(Guia guia, Long autorId) {
        Usuario autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado com ID: " + autorId));
        guia.setAutor(autor);
        if (guia.getPlantasAssociadas() == null) {
            guia.setPlantasAssociadas(new java.util.HashSet<>());
        }
        return guiaRepository.save(guia);
    }

    @Transactional
    public Guia atualizarGuia(Long id, Guia guiaAtualizado) {
        return guiaRepository.findById(id).map(guiaExistente -> {
            guiaExistente.setGuiaTitulo(guiaAtualizado.getGuiaTitulo());
            guiaExistente.setGuiaConteudo(guiaAtualizado.getGuiaConteudo());
            guiaExistente.setGuiaImagemUrl(guiaAtualizado.getGuiaImagemUrl());
            guiaExistente.setGuiaTipo(guiaAtualizado.getGuiaTipo());
            return guiaRepository.save(guiaExistente);
        }).orElseThrow(() -> new IllegalArgumentException("Guia não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarGuia(Long id) {
        if (!guiaRepository.existsById(id)) {
            throw new IllegalArgumentException("Guia não encontrado com ID: " + id);
        }
        guiaRepository.deleteById(id);
    }

    @Transactional
    public Guia associarPlantaAoGuia(Long guiaId, Long plantaId) {
        Guia guia = guiaRepository.findById(guiaId)
                .orElseThrow(() -> new EntityNotFoundException("Guia não encontrado com ID: " + guiaId));

        Planta planta = plantaRepository.findById(plantaId)
                .orElseThrow(() -> new EntityNotFoundException("Planta não encontrada com ID: " + plantaId));

        if (guia.getPlantasAssociadas().add(planta)) {
            planta.getGuias().add(guia);
        } else {
            System.out.println("Planta " + plantaId + " já está associada ao guia " + guiaId);
        }
        return guiaRepository.save(guia);
    }

    @Transactional
    public Guia desassociarPlantaDoGuia(Long guiaId, Long plantaId) {
        Guia guia = guiaRepository.findById(guiaId)
                .orElseThrow(() -> new EntityNotFoundException("Guia não encontrado com ID: " + guiaId));

        Planta planta = plantaRepository.findById(plantaId)
                .orElseThrow(() -> new EntityNotFoundException("Planta não encontrada com ID: " + plantaId));

        if (guia.getPlantasAssociadas().remove(planta)) {
            planta.getGuias().remove(guia);
        } else {
            System.out.println("Planta " + plantaId + " não estava associada ao guia " + guiaId);
        }
        return guiaRepository.save(guia);
    }
}