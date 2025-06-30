package com.horte.service;

import com.horte.model.Role;
import com.horte.model.RoleName;
import com.horte.model.Usuario;
import com.horte.repository.RoleRepository;
import com.horte.repository.UsuarioRepository;
import com.horte.dto.UsuarioProfileResponse;
import com.horte.dto.UsuarioResponse;
import com.horte.dto.UsuarioUpdateRequest;
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
    public UsuarioResponse atualizarUsuario(Long id, UsuarioUpdateRequest usuarioAtualizadoDto) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            if (usuarioAtualizadoDto.getUsername() != null && !usuarioAtualizadoDto.getUsername().isBlank()) {
                if (usuarioRepository.findByUsername(usuarioAtualizadoDto.getUsername())
                                     .filter(u -> !u.getId().equals(id))
                                     .isPresent()) {
                    throw new IllegalArgumentException("Nome de usuário já está em uso por outro usuário.");
                }
                usuarioExistente.setUsername(usuarioAtualizadoDto.getUsername());
            }
            if (usuarioAtualizadoDto.getUsuarioEmail() != null && !usuarioAtualizadoDto.getUsuarioEmail().isBlank()) {
                if (usuarioRepository.findByUsuarioEmail(usuarioAtualizadoDto.getUsuarioEmail())
                                     .filter(u -> !u.getId().equals(id))
                                     .isPresent()) {
                    throw new IllegalArgumentException("E-mail já está em uso por outro usuário.");
                }
                usuarioExistente.setUsuarioEmail(usuarioAtualizadoDto.getUsuarioEmail());
            }
            if (usuarioAtualizadoDto.getUsuarioImagemUrl() != null) {
                usuarioExistente.setUsuarioImagemUrl(usuarioAtualizadoDto.getUsuarioImagemUrl());
            }
            if (usuarioAtualizadoDto.getLocalizacao() != null) {
                usuarioExistente.setLocalizacao(usuarioAtualizadoDto.getLocalizacao());
            }
            if (usuarioAtualizadoDto.getHortaTipo() != null) {
                usuarioExistente.setHortaTipo(usuarioAtualizadoDto.getHortaTipo());
            }
            
            Usuario usuarioSalvo = usuarioRepository.save(usuarioExistente);
            return mapUsuarioToUsuarioResponse(usuarioSalvo);
        }).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
    }

    @Transactional
    public void deletarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado com ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

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