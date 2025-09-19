package com.protectora.backend.services;

import com.protectora.backend.model.Usuario;
import com.protectora.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Obtener todos los usuarios
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    // Guardar o actualizar usuario
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario por ID
    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // Buscar usuario por email (para login)
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Buscar usuario por nombre (para Spring Security)
    public Optional<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
}
