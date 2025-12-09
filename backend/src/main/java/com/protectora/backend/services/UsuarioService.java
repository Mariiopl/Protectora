package com.protectora.backend.services;

import com.protectora.backend.dto.UsuarioUpdateDto;
import com.protectora.backend.model.Empleado;
import com.protectora.backend.model.Usuario;
import com.protectora.backend.repository.EmpleadoRepository;
import com.protectora.backend.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmpleadoRepository empleadoRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
            EmpleadoRepository empleadoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.empleadoRepository = empleadoRepository;
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

    @Transactional
    public Usuario updateUsuario(Integer id, UsuarioUpdateDto dto) {
        // 1. Buscar usuario
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Actualizar campos básicos
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

        Usuario actualizado = usuarioRepository.save(u);

        // 3. Si es empleado, crear o actualizar Empleado
        if (dto.getTipoUsuario() == Usuario.TipoUsuario.empleado) {
            Empleado empleado = empleadoRepository.findById(actualizado.getIdUsuario())
                    .orElseGet(() -> {
                        Empleado e = new Empleado();
                        e.setUsuario(actualizado);
                        e.setFechaAlta(LocalDate.now());
                        return e;
                    });

            // 4. Asignar rol desde DTO y calcular salario según rol
            if (dto.getRolEmpleado() != null) {
                empleado.setRol(dto.getRolEmpleado());
                empleado.setSalario(dto.getRolEmpleado().getSalario());
                empleado.setHorario(Empleado.Rol.adopciones.getHorario());
            } else {
                // Si no se manda rol, asignamos adopciones por defecto
                empleado.setRol(Empleado.Rol.adopciones);
                empleado.setSalario(Empleado.Rol.adopciones.getSalario());
                empleado.setHorario(Empleado.Rol.adopciones.getHorario());
            }

            empleadoRepository.save(empleado);
        }

        return actualizado;
    }

}
