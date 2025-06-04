package com.horte.service;

import com.horte.model.Usuario;
import com.horte.model.Post;

import com.horte.repository.UsuarioRepository;
import com.horte.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Post> listarTodosPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "postData"));
    }

    public Optional<Post> buscarPostPorId(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public Post criarPost(Post post, Long autorId) {
        Usuario autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado com ID: " + autorId));
        post.setAutor(autor);
        post.setPostData(LocalDate.now());
        return postRepository.save(post);
    }

    @Transactional
    public Post atualizarPost(Long id, Post postAtualizado) {
        return postRepository.findById(id).map(postExistente -> {
            postExistente.setPostTexto(postAtualizado.getPostTexto());
            postExistente.setPostImagemUrl(postAtualizado.getPostImagemUrl());
            return postRepository.save(postExistente);
        }).orElseThrow(() -> new IllegalArgumentException("Post não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarPost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post não encontrado com ID: " + id);
        }
        postRepository.deleteById(id);
    }
}
