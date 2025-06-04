package com.horte.repository;

import com.horte.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // JpaRepository<Entidade, TipoDoID> fornece métodos CRUD básicos (save, findById, findAll, delete, etc.)

    Optional<Usuario> findByUsername(String username); // Para login, busca por username
    Optional<Usuario> findByUsuarioEmail(String usuarioEmail); // Para verificar email duplicado no cadastro
}
