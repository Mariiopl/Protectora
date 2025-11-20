package com.protectora.backend.services;

import com.protectora.backend.dto.UsuarioUpdateDto;
import com.protectora.backend.model.Usuario;
import com.protectora.backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Obtener todos los usuarios
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    // Guardar o actualizar usuario (sin encriptar)
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Guardar nuevo usuario con contraseña encriptada
    public Usuario register(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    // Login: devuelve usuario si la contraseña coincide
    public Optional<Usuario> login(String email, String password) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getContrasena())) {
            return userOpt;
        }
        return Optional.empty();
    }

    // Eliminar usuario por ID
    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // Buscar usuario por email
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Buscar usuario por nombre
    public Optional<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    public Usuario updateUsuario(Integer id, UsuarioUpdateDto dto) {

        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (dto.getNombre() != null)
            u.setNombre(dto.getNombre());
        if (dto.getEmail() != null)
            u.setEmail(dto.getEmail());
        if (dto.getTelefono() != null)
            u.setTelefono(dto.getTelefono());
        if (dto.getDireccion() != null)
            u.setDireccion(dto.getDireccion());
        if (dto.getTipoUsuario() != null)
            u.setTipoUsuario(dto.getTipoUsuario());

        return usuarioRepository.save(u);
    }

}
