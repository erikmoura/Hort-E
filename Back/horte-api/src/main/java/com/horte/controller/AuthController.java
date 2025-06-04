package com.horte.controller;

import com.horte.dto.JwtResponse;
import com.horte.dto.LoginRequest;
import com.horte.dto.UsuarioRegisterRequest;
import com.horte.dto.UsuarioResponse;
import com.horte.model.Usuario;
import com.horte.service.UsuarioService;
import com.horte.security.jwt.JwtTokenUtil;
import com.horte.security.CustomUserDetailsService;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// Controlador pra gerenciar autenticação e registro de usuários

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários na API Plant Guide")
public class AuthController {

    // Seção de injeção de dependências.

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Operation(
        // As anotações @Operation e @ApiResponse do Swagger já descrevem bem o propósito do método e suas respostas.
        summary = "Registrar um novo usuário",
        description = "Cria uma nova conta de usuário no sistema Plant Guide com as informações fornecidas.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Usuário registrado com sucesso. Retorna os detalhes do usuário criado.",
                content = @Content(schema = @Schema(implementation = UsuarioResponse.class))
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Requisição inválida (e.g., dados faltando ou inválidos) ou usuário/e-mail já em uso.",
                content = @Content(schema = @Schema(example = "{\"message\": \"Nome de usuário já está em uso.\"}"))
            )
        }
    )
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsuarioRegisterRequest registerRequest) {
        try {
            Usuario usuario = new Usuario();
            // Copia as propriedades do DTO para um usuário
            BeanUtils.copyProperties(registerRequest, usuario);

            // Chama o serviço.
            Usuario novoUsuario = usuarioService.cadastrarUsuario(usuario);
            
            UsuarioResponse responseDto = new UsuarioResponse();
            // Copia de volta para o DTO
            BeanUtils.copyProperties(novoUsuario, responseDto);

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Retorna erro caso nome de usuário e email ja estejam no banco de dados
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Operation(
        summary = "Autenticar usuário e gerar JWT",
        description = "Realiza o login do usuário utilizando nome de usuário e senha. Em caso de sucesso, retorna um JSON Web Token (JWT) que deve ser usado para acessar endpoints protegidos.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Login bem-sucedido. O token JWT é fornecido na resposta.",
                content = @Content(schema = @Schema(implementation = JwtResponse.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Credenciais inválidas (username ou senha incorretos)."
            )
        }
    )
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Processo de segurança do spring. Cria um token com as informações fornecidas para passar para o cliente e ele provar que está logado.
            Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Obtém os detalhes completos do usuário autenticado, incluindo suas permissões.
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            
            // Gera o JWT usando os detalhes do usuário, que será retornado ao cliente.
            String jwt = jwtTokenUtil.generateToken(userDetails);

            // Busca outras informmações do usuário para colocar na resposta do login para uso posterior.
            Usuario usuario = usuarioService.buscarPorUsername(userDetails.getUsername())
                                        .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado após autenticação."));

            return ResponseEntity.ok(new JwtResponse(jwt, "Bearer", usuario.getId(), usuario.getUsername(), usuario.getUsuarioEmail()));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciais inválidas."));
        }
    }
}