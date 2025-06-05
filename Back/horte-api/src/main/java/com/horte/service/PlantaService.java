package com.horte.service;

import com.horte.model.Planta;
import com.horte.dto.PlantaResponse;
import com.horte.repository.PlantaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlantaService {

    @Autowired
    private PlantaRepository plantaRepository;

    public List<PlantaResponse> listarTodasPlantasDTO() {
        return plantaRepository.findAll().stream()
                .map(PlantaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<PlantaResponse> buscarPlantaPorIdDTO(Long id) {
        return plantaRepository.findById(id)
                .map(PlantaResponse::fromEntity);
    }

    public List<PlantaResponse> buscarPlantasPorFiltro(
            String categoria, String tipoSolo, String irrigacao,
            String localPlantio, String clima, String luzSolar) {

        Specification<Planta> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        if (StringUtils.hasText(categoria) && !"todos".equalsIgnoreCase(categoria)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("categoria")), categoria.toLowerCase()));
        }
        if (StringUtils.hasText(tipoSolo) && !"todos".equalsIgnoreCase(tipoSolo)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("tipoSolo")), tipoSolo.toLowerCase()));
        }
        if (StringUtils.hasText(irrigacao) && !"todos".equalsIgnoreCase(irrigacao)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("irrigacao")), irrigacao.toLowerCase()));
        }
        if (StringUtils.hasText(localPlantio) && !"todos".equalsIgnoreCase(localPlantio)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("localPlantio")), localPlantio.toLowerCase()));
        }
        if (StringUtils.hasText(clima) && !"todos".equalsIgnoreCase(clima)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("clima")), clima.toLowerCase()));
        }
        if (StringUtils.hasText(luzSolar) && !"todos".equalsIgnoreCase(luzSolar)) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("luzSolar")), luzSolar.toLowerCase()));
        }

        return plantaRepository.findAll(spec).stream()
                .map(PlantaResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public Planta criarPlanta(Planta planta) {
        return plantaRepository.save(planta);
    }

    @Transactional
    public Planta atualizarPlanta(Long id, Planta plantaAtualizada) {
        return plantaRepository.findById(id).map(plantaExistente -> {
            plantaExistente.setNomeComum(plantaAtualizada.getNomeComum());
            plantaExistente.setNomeCientifico(plantaAtualizada.getNomeCientifico());
            plantaExistente.setCategoria(plantaAtualizada.getCategoria());
            plantaExistente.setPlantaImagemUrl(plantaAtualizada.getPlantaImagemUrl());
            plantaExistente.setDescricao(plantaAtualizada.getDescricao());
            plantaExistente.setTipoSolo(plantaAtualizada.getTipoSolo());
            plantaExistente.setIrrigacao(plantaAtualizada.getIrrigacao());
            plantaExistente.setLocalPlantio(plantaAtualizada.getLocalPlantio());
            plantaExistente.setClima(plantaAtualizada.getClima());
            plantaExistente.setLuzSolar(plantaAtualizada.getLuzSolar());
            return plantaRepository.save(plantaExistente);
        }).orElseThrow(() -> new IllegalArgumentException("Planta não encontrada com ID: " + id));
    }

    @Transactional
    public void deletarPlanta(Long id) {
        if (!plantaRepository.existsById(id)) {
            throw new IllegalArgumentException("Planta não encontrada com ID: " + id);
        }
        plantaRepository.deleteById(id);
    }
}