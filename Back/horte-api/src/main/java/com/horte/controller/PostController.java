package com.horte.controller;

import com.horte.dto.ComentarioCreateRequest;
import com.horte.dto.PostCreateRequest;
import com.horte.model.Comentario;
import com.horte.model.Post;
import com.horte.model.Usuario;
import com.horte.service.ComentarioService;
import com.horte.service.PostService;
import com.horte.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

// Controlador REST que gerencia posts e comentários.

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Gerenciamento de posts e comentários em posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private ComentarioService comentarioService;
    @Autowired
    private UsuarioService usuarioService;
    
    // Endpoints de Posts

    @Operation(
        summary = "Listar todos os posts",
        description = "Retorna uma lista de todos os posts disponíveis na plataforma."
    )
    @ApiResponse(responseCode = "200", description = "Lista de posts retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.listarTodosPosts();
        return ResponseEntity.ok(posts);
    }

    @Operation(
        summary = "Buscar post por ID",
        description = "Retorna um post específico dado seu ID.",
        parameters = @Parameter(name = "id", description = "ID do post a ser buscado", required = true, example = "1")
    )
    @ApiResponse(responseCode = "200", description = "Post encontrado com sucesso")
    @ApiResponse(responseCode = "404", description = "Post não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.buscarPostPorId(id);
        return post.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Criar um novo post",
        description = "Cria um novo post. Requer que o usuário esteja autenticado (ROLE_USER ou ROLE_ADMIN).",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "201", description = "Post criado com sucesso",
                 content = @Content(schema = @Schema(implementation = Post.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (requer ROLE_USER ou ROLE_ADMIN)")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostCreateRequest postRequest, @AuthenticationPrincipal UserDetails currentUser) {
        Post post = new Post();
        BeanUtils.copyProperties(postRequest, post);

        // Busca o objeto Usuario completo usando o nome de usuário do usuário autenticado.
        Usuario autor = usuarioService.buscarPorUsername(currentUser.getUsername())
                                       .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não encontrado."));

        Post novoPost = postService.criarPost(post, autor.getId());
        return new ResponseEntity<>(novoPost, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Atualizar um post existente",
        description = "Atualiza um post existente. Apenas o autor do post ou um ADMIN pode realizar esta operação.",
        parameters = @Parameter(name = "id", description = "ID do post a ser atualizado", required = true, example = "1"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Post atualizado com sucesso",
                 content = @Content(schema = @Schema(implementation = Post.class)))
    @ApiResponse(responseCode = "404", description = "Post não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (não é o autor ou não tem ROLE_ADMIN)")
    @PreAuthorize("hasRole('ADMIN') or @postService.buscarPostPorId(#id).orElse(null)?.autor?.username == authentication.name")
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        try {
            Post postAtualizado = postService.atualizarPost(id, post);
            return ResponseEntity.ok(postAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Deletar um post",
        description = "Deleta um post existente. Apenas o autor do post ou um ADMIN pode realizar esta operação.",
        parameters = @Parameter(name = "id", description = "ID do post a ser deletado", required = true, example = "1"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "204", description = "Post deletado com sucesso (sem conteúdo)")
    @ApiResponse(responseCode = "404", description = "Post não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (não é o autor ou não tem ROLE_ADMIN)")
    @PreAuthorize("hasRole('ADMIN') or @postService.buscarPostPorId(#id).orElse(null)?.autor?.username == authentication.name")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletarPost(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoints de Comentários

    @Operation(
        summary = "Listar comentários de um post",
        description = "Retorna todos os comentários associados a um post específico.",
        parameters = @Parameter(name = "postId", description = "ID do post para buscar os comentários", required = true, example = "1")
    )
    @ApiResponse(responseCode = "200", description = "Lista de comentários retornada com sucesso")
    @ApiResponse(responseCode = "404", description = "Post não encontrado")
    @GetMapping("/{postId}/comentarios")
    public ResponseEntity<List<Comentario>> getComentariosByPost(@PathVariable Long postId) {
        if (postService.buscarPostPorId(postId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Comentario> comentarios = comentarioService.listarComentariosPorPost(postId);
        return ResponseEntity.ok(comentarios);
    }

    @Operation(
        summary = "Adicionar um comentário a um post",
        description = "Adiciona um novo comentário a um post específico. Requer que o usuário esteja autenticado (ROLE_USER ou ROLE_ADMIN).",
        parameters = @Parameter(name = "postId", description = "ID do post para adicionar o comentário", required = true, example = "1"),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "201", description = "Comentário adicionado com sucesso",
                 content = @Content(schema = @Schema(implementation = Comentario.class)))
    @ApiResponse(responseCode = "400", description = "Requisição inválida ou post não encontrado")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Não autorizado (requer ROLE_USER ou ROLE_ADMIN)")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/{postId}/comentarios")
    public ResponseEntity<Comentario> addComentario(@PathVariable Long postId,
                                                     @Valid @RequestBody ComentarioCreateRequest comentarioRequest,
                                                     @AuthenticationPrincipal UserDetails currentUser) {
        Comentario comentario = new Comentario();
        BeanUtils.copyProperties(comentarioRequest, comentario);

        // Associa o comentário ao usuário autenticado como seu autor.
        Usuario autor = usuarioService.buscarPorUsername(currentUser.getUsername())
                                       .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não encontrado."));

        try {
            Comentario novoComentario = comentarioService.criarComentario(postId, comentario, autor.getId());
            return new ResponseEntity<>(novoComentario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}