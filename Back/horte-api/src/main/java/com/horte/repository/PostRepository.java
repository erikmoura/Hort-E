package com.horte.repository;

import com.horte.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Para buscar posts de um usuário específico
    // List<Post> findByAutor(Usuario autor);
}
