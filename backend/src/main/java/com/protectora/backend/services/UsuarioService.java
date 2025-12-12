package com.protectora.backend.services;

import com.protectora.backend.dto.UsuarioUpdateDto;
import com.protectora.backend.model.Empleado;
import com.protectora.backend.model.Usuario;
import com.protectora.backend.repository.EmpleadoRepository;
import com.protectora.backend.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    // =============================
    // CONSULTAS
    // =============================

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // =============================
    // REGISTRO
    // =============================

    public Usuario register(Usuario usuario) {

        // Validar email único
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        // Validar teléfono único
        if (usuario.getTelefono() != null &&
                usuarioRepository.existsByTelefono(usuario.getTelefono())) {
            throw new RuntimeException("El número de teléfono ya está registrado.");
        }

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    // =============================
    // LOGIN
    // =============================

    public Optional<Usuario> login(String email, String password) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(email);
        if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getContrasena())) {
            return userOpt;
        }
        return Optional.empty();
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    // =============================
    // ACTUALIZAR USUARIO
    // =============================

    @Transactional
    public Usuario updateUsuario(Integer id, UsuarioUpdateDto dto) {

        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // --- Validación de email ---
        if (dto.getEmail() != null && !dto.getEmail().equals(u.getEmail())) {
            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new RuntimeException("El correo ya está registrado por otro usuario.");
            }
            u.setEmail(dto.getEmail());
        }

        // --- Validación de teléfono ---
        if (dto.getTelefono() != null && !dto.getTelefono().equals(u.getTelefono())) {
            if (usuarioRepository.existsByTelefono(dto.getTelefono())) {
                throw new RuntimeException("El teléfono ya está registrado por otro usuario.");
            }
            u.setTelefono(dto.getTelefono());
        }

        // Otros campos
        if (dto.getNombre() != null)
            u.setNombre(dto.getNombre());
        if (dto.getDireccion() != null)
            u.setDireccion(dto.getDireccion());
        if (dto.getTipoUsuario() != null)
            u.setTipoUsuario(dto.getTipoUsuario());

        Usuario actualizado = usuarioRepository.save(u);

        // =============================
        // SI ES EMPLEADO -> CREAR / ACTUALIZAR
        // =============================

        if (dto.getTipoUsuario() == Usuario.TipoUsuario.empleado) {

            Empleado empleado = empleadoRepository.findById(actualizado.getIdUsuario())
                    .orElseGet(() -> {
                        Empleado e = new Empleado();
                        e.setUsuario(actualizado);
                        e.setFechaAlta(LocalDate.now());
                        return e;
                    });

            // Asignar rol
            if (dto.getRolEmpleado() != null) {
                empleado.setRol(dto.getRolEmpleado());
                empleado.setSalario(dto.getRolEmpleado().getSalario());
                empleado.setHorario(dto.getRolEmpleado().getHorario());
            } else {
                empleado.setRol(Empleado.Rol.adopciones);
                empleado.setSalario(Empleado.Rol.adopciones.getSalario());
                empleado.setHorario(Empleado.Rol.adopciones.getHorario());
            }

            empleadoRepository.save(empleado);
        }

        return actualizado;
    }

}
