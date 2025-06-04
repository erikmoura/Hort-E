package com.horte.service;

import com.horte.model.Role;
import com.horte.model.RoleName;
import com.horte.model.Usuario;
import com.horte.repository.RoleRepository;
import com.horte.repository.UsuarioRepository;
import com.horte.dto.UsuarioProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Nome de usuário já está em uso.");
        }
        if (usuarioRepository.findByUsuarioEmail(usuario.getUsuarioEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER) 
            .orElseThrow(() -> new RuntimeException("Erro: Role de usuário não encontrada."));
        roles.add(userRole);

        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    /**
     * Busca um usuário pelo username e retorna um DTO com suas informações de perfil.
     * @param username O username do usuário a ser buscado.
     * @return Um Optional contendo UsuarioProfileResponse se o usuário for encontrado, ou vazio se não for.
     */
    public Optional<UsuarioProfileResponse> buscarPerfilUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
            .map(UsuarioProfileResponse::fromEntity);
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setUsername(usuarioAtualizado.getUsername());
            usuarioExistente.setUsuarioEmail(usuarioAtualizado.getUsuarioEmail());
            usuarioExistente.setUsuarioImagemUrl(usuarioAtualizado.getUsuarioImagemUrl());
            usuarioExistente.setLocalizacao(usuarioAtualizado.getLocalizacao());
            usuarioExistente.setHortaTipo(usuarioAtualizado.getHortaTipo());
            // Não atualiza as roles aqui. Nem as senhas. Se for mudar faz em outro método.
            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}