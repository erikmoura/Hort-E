package com.horte.repository;

import com.horte.model.Planta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantaRepository extends JpaRepository<Planta, Long> {
    // List<Planta> findByCategoria(String categoria);
    // List<Planta> findByNomeComumContainingIgnoreCase(String nomeComum);
}
