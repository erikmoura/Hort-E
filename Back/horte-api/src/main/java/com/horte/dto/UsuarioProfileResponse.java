package com.horte.dto;

import com.horte.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


//DTO para representar informações de perfil de usuário a serem retornadas em endpoints de consulta.

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioProfileResponse {
    private String username;
    private String usuarioImagemUrl;
    private String localizacao;
    private Integer hortaTipo;

    public static UsuarioProfileResponse fromEntity(Usuario usuario) {
        UsuarioProfileResponse dto = new UsuarioProfileResponse();
        dto.setUsername(usuario.getUsername());
        dto.setUsuarioImagemUrl(usuario.getUsuarioImagemUrl());
        dto.setLocalizacao(usuario.getLocalizacao());
        dto.setHortaTipo(usuario.getHortaTipo());
        return dto;
    }
}