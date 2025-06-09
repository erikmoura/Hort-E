package com.horte.service;

import com.horte.model.Usuario;
import com.horte.model.Post;
import com.horte.model.Role;
import com.horte.model.RoleName;

import com.horte.repository.UsuarioRepository;
import com.horte.repository.PostRepository;

import com.horte.dto.PostResponse;
import com.horte.dto.UsuarioResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<PostResponse> listarTodosPosts() {
        return postRepository.findAll().stream()
                .map(this::mapPostToPostResponse)
                .collect(Collectors.toList());
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

    private PostResponse mapPostToPostResponse(Post post) {
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        UsuarioResponse dto = new UsuarioResponse();
            dto.setId(post.getAutor().getId());
            dto.setUsername(post.getAutor().getUsername());
            dto.setUsuarioEmail(post.getAutor().getUsuarioEmail());
            dto.setUsuarioImagemUrl(post.getAutor().getUsuarioImagemUrl());
            dto.setLocalizacao(post.getAutor().getLocalizacao());
            dto.setHortaTipo(post.getAutor().getHortaTipo());
            dto.setRoles(post.getAutor().getRoles().stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.toSet())); 
        response.setAutor(dto);
        response.setPostTexto(post.getPostTexto());
        response.setPostData(post.getPostData().toString());
        response.setPostImagemUrl(post.getPostImagemUrl());
        response.setComentarios(post.getComentarios());
        return response;
    }
}
