package com.horte.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa uma role (papel/permissão) de usuário no sistema, utilizada para controle de acesso.")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único da role.", example = "1")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Schema(description = "Nome da role (ROLE_USER, ROLE_ADMIN).", example = "ROLE_USER")
    private RoleName name;

    @Override
    public String getAuthority() {
        return name.name();
    }
}