package com.protectora.backend.controller;

import com.protectora.backend.model.Usuario;
import com.protectora.backend.security.JwtTokenProvider;
import com.protectora.backend.services.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Controlador para autenticación de usuarios.
 * Permite registrar, iniciar sesión y generar tokens JWT.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;

    // Constructor con inyección de dependencias
    public AuthController(UsuarioService usuarioService, JwtTokenProvider jwtTokenProvider) {
        this.usuarioService = usuarioService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // =================================================
    // REGISTRO DE USUARIO
    // =================================================

    /**
     * Registro de un nuevo usuario.
     * 
     * @param usuario objeto Usuario a registrar
     * @return usuario registrado
     */
    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        return usuarioService.register(usuario);
    }

    // =================================================
    // LOGIN DE USUARIO
    // =================================================

    /**
     * Login de usuario.
     * Verifica credenciales y devuelve token JWT + información básica.
     * 
     * @param loginRequest DTO con email y contraseña
     * @return Map con token, nombre y tipo de usuario
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        // Intentar autenticar usuario
        Optional<Usuario> userOpt = usuarioService.login(loginRequest.getEmail(), loginRequest.getContrasena());

        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();

            // Generar token JWT
            String token = jwtTokenProvider.generateToken(user);

            // Devolver token junto con datos básicos del usuario
            return Map.of(
                    "token", token,
                    "nombre", user.getNombre(),
                    "tipoUsuario", user.getTipoUsuario());
        } else {
            // Si falla la autenticación, lanzar excepción
            throw new RuntimeException("Email o contraseña incorrectos");
        }
    }

    // =================================================
    // DTO PARA LOGIN
    // =================================================

    /**
     * DTO para recibir datos de login desde el frontend.
     */
    public static class LoginRequest {
        private String email;
        private String contrasena;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }
}
