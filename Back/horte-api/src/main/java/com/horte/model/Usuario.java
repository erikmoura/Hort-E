package com.horte.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"senha", "roles", "guiasCriados", "postsCriados", "comentariosFeitos"})
@EqualsAndHashCode(exclude = {"senha", "roles", "guiasCriados", "postsCriados", "comentariosFeitos"})
@Schema(description = "Representa um usuário cadastrado no sistema Plant Guide, incluindo seus detalhes de perfil e roles de acesso.")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    @Schema(description = "Identificador único do usuário.", example = "1")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @Schema(description = "Nome de usuário único.", example = "floricultura_urbana")
    private String username;

    @Column(name = "senha", nullable = false)
    @Schema(description = "Senha criptografada do usuário. **Este campo não é exposto em respostas da API.**", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String senha;

    @Column(name = "usuario_email", nullable = false, unique = true)
    @Schema(description = "Endereço de e-mail único do usuário.", example = "floricultura@example.com")
    private String usuarioEmail;

    @Column(name = "usuario_imagem_url")
    @Schema(description = "URL opcional para a imagem de perfil do usuário.",
            example = "https://example.com/imagens/perfil/floricultura.jpg",
            nullable = true)
    private String usuarioImagemUrl;

    @Column(name = "localizacao")
    @Schema(description = "Localização do usuário (cidade, estado, etc.).", example = "Niterói, RJ", nullable = true)
    private String localizacao;

    @Column(name = "horta_tipo")
    @Schema(description = "Tipo de horta que o usuário possui ou se interessa (ex: 1 para jardim, 2 para interna, 3 para ambos).",
            example = "1",
            allowableValues = {"1", "2", "3"},
            nullable = true)
    private Integer hortaTipo;
    
    // Relacionamento Many-to-Many com Role
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Schema(description = "Conjunto de roles (papéis de permissão) atribuídas ao usuário.")
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de guias de plantas criados por este usuário.")
    @JsonManagedReference("usuario-guias")
    private List<Guia> guiasCriados = new ArrayList<>();

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de posts criados por este usuário.")
    @JsonManagedReference("usuario-posts")
    private List<Post> postsCriados = new ArrayList<>();

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de comentários feitos por este usuário.")
    @JsonManagedReference("usuario-comentarios")
    private List<Comentario> comentariosFeitos = new ArrayList<>();

    public Usuario(String username, String senha, String usuarioEmail) {
        this.username = username;
        this.senha = senha;
        this.usuarioEmail = usuarioEmail;
        this.roles = new HashSet<>();
        this.guiasCriados = new ArrayList<>();
        this.postsCriados = new ArrayList<>();
        this.comentariosFeitos = new ArrayList<>();
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }
}