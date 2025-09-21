package com.protectora.backend.controller;

import com.protectora.backend.model.Usuario;
import com.protectora.backend.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200") // Permite peticiones desde Angular
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    @Autowired
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Registro de usuario
    @PostMapping("/register")
    public Usuario register(@RequestBody Usuario usuario) {
        return usuarioService.register(usuario);
    }

    // Login de usuario
    @PostMapping("/login")
    public Usuario login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> userOpt = usuarioService.login(loginRequest.getEmail(), loginRequest.getContrasena());
        if (userOpt.isPresent()) {
            return userOpt.get();
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
