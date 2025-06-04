package com.horte.service;

import com.horte.model.Planta;

import com.horte.repository.PlantaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlantaService {

    @Autowired
    private PlantaRepository plantaRepository;

    public List<Planta> listarTodasPlantas() {
        return plantaRepository.findAll();
    }

    public Optional<Planta> buscarPlantaPorId(Long id) {
        return plantaRepository.findById(id);
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
