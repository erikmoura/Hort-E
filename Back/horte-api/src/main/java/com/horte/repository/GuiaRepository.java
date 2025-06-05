package com.horte.repository;

import com.horte.model.Guia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GuiaRepository extends JpaRepository<Guia, Long>, JpaSpecificationExecutor<Guia> {

}