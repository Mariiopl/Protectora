package com.protectora.backend.controller;

import com.protectora.backend.model.Usuario;
import com.protectora.backend.security.JwtTokenProvider;
import com.protectora.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(UsuarioService usuarioService, JwtTokenProvider jwtTokenProvider) {
        this.usuarioService = usuarioService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Registro de usuario
    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        return usuarioService.register(usuario);
    }

    // Login de usuario (devuelve token + info)
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> userOpt = usuarioService.login(loginRequest.getEmail(), loginRequest.getContrasena());
        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();

            // Generar token JWT
            String token = jwtTokenProvider.generateToken(user);

            // Devolver token + nombre + rol
            return Map.of(
                    "token", token,
                    "nombre", user.getNombre(),
                    "tipoUsuario", user.getTipoUsuario());
        } else {
            throw new RuntimeException("Email o contraseña incorrectos");
        }
    }

    // DTO para recibir datos de login
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
