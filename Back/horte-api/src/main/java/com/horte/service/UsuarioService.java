package com.horte.service;

import com.horte.model.Role;
import com.horte.model.RoleName;
import com.horte.model.Usuario;
import com.horte.repository.RoleRepository;
import com.horte.repository.UsuarioRepository;
import com.horte.dto.UsuarioProfileResponse;
import com.horte.dto.UsuarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Optional<UsuarioProfileResponse> buscarPerfilUsuarioPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
            .map(UsuarioProfileResponse::fromEntity);
    }

    public Optional<UsuarioProfileResponse> buscarPerfilUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
            .map(UsuarioProfileResponse::fromEntity);
    }

    // Listar todos os usuários (retornando DTOs)
    public List<UsuarioResponse> listarTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::mapUsuarioToUsuarioResponse)
                .collect(Collectors.toList());
    }

    // Tornar um usuário administrador
    @Transactional
    public UsuarioResponse tornarAdministrador(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));

        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Erro: Role de administrador não encontrada."));

        if (!usuario.getRoles().contains(adminRole)) {
            usuario.addRole(adminRole);
            usuarioRepository.save(usuario);
        } else {
            System.out.println("Usuário com ID " + id + " já é administrador.");
        }
        return mapUsuarioToUsuarioResponse(usuario);
    }

    @Transactional
        public UsuarioResponse atualizarUsuario(Long id, Usuario usuarioAtualizado) {
            Usuario usuarioExistente = usuarioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));

            usuarioExistente.setUsername(usuarioAtualizado.getUsername());
            usuarioExistente.setUsuarioEmail(usuarioAtualizado.getUsuarioEmail());
            usuarioExistente.setUsuarioImagemUrl(usuarioAtualizado.getUsuarioImagemUrl());
            usuarioExistente.setLocalizacao(usuarioAtualizado.getLocalizacao());
            usuarioExistente.setHortaTipo(usuarioAtualizado.getHortaTipo());
            
            Usuario usuarioSalvo = usuarioRepository.save(usuarioExistente);

            return mapUsuarioToUsuarioResponse(usuarioSalvo);
        }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // Método auxiliar para mapear Usuario para UsuarioResponse
    private UsuarioResponse mapUsuarioToUsuarioResponse(Usuario usuario) {
        UsuarioResponse dto = new UsuarioResponse();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setUsuarioEmail(usuario.getUsuarioEmail());
        dto.setUsuarioImagemUrl(usuario.getUsuarioImagemUrl());
        dto.setLocalizacao(usuario.getLocalizacao());
        dto.setHortaTipo(usuario.getHortaTipo());
        dto.setRoles(usuario.getRoles().stream()
                            .map(role -> role.getName().name())
                            .collect(Collectors.toSet())); 
        return dto;
    }
}