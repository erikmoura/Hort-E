package com.horte.repository;

import com.horte.model.Guia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, Long> {
    // List<Guia> findByAutor(Usuario autor); // Para buscar guias de um usuário específico
    // List<Guia> findByGuiaTipo(Integer tipo); // Para filtrar guias por tipo (jardim, interior, ambos)
}
