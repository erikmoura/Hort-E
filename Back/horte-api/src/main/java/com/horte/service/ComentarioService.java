package com.horte.service;

import com.horte.model.Usuario;
import com.horte.model.Post;
import com.horte.model.Comentario;

import com.horte.repository.UsuarioRepository;
import com.horte.repository.PostRepository;
import com.horte.repository.ComentarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Comentario> listarComentariosPorPost(Long postId) {
        return comentarioRepository.findByPostId(postId);
    }

    public Optional<Comentario> buscarComentarioPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    @Transactional
    public Comentario criarComentario(Long postId, Comentario comentario, Long autorId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post não encontrado com ID: " + postId));
        Usuario autor = usuarioRepository.findById(autorId)
                .orElseThrow(() -> new IllegalArgumentException("Autor não encontrado com ID: " + autorId));

        comentario.setPost(post);
        comentario.setAutor(autor);
        comentario.setComentarioData(LocalDate.now());
        return comentarioRepository.save(comentario);
    }

    @Transactional
    public Comentario atualizarComentario(Long id, Comentario comentarioAtualizado) {
        return comentarioRepository.findById(id).map(comentarioExistente -> {
            comentarioExistente.setComentarioTexto(comentarioAtualizado.getComentarioTexto());
            return comentarioRepository.save(comentarioExistente);
        }).orElseThrow(() -> new IllegalArgumentException("Comentário não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarComentario(Long id) {
        if (!comentarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Comentário não encontrado com ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }
}