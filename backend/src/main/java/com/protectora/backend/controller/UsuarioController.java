package com.protectora.backend.controller;

import com.protectora.backend.dto.UsuarioUpdateDto;
import com.protectora.backend.model.Usuario;
import com.protectora.backend.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de usuarios.
 * Permite crear, listar, actualizar y eliminar usuarios.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200") // Permitimos llamadas desde Angular
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // =================================================
    // LISTAR TODOS LOS USUARIOS
    // =================================================
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    // =================================================
    // OBTENER USUARIO POR ID
    // =================================================
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Integer id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =================================================
    // CREAR NUEVO USUARIO
    // =================================================
    /**
     * Crea un nuevo usuario en la base de datos.
     * 
     * @param usuario Objeto Usuario recibido desde el body
     * @return Usuario creado con código HTTP 201
     */
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario saved = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // =================================================
    // ACTUALIZAR USUARIO EXISTENTE
    // =================================================
    /**
     * Actualiza un usuario existente usando DTO para modificar solo los campos
     * permitidos.
     * 
     * @param id  ID del usuario a actualizar
     * @param dto DTO con los campos a actualizar
     * @return Usuario actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioUpdateDto dto) {
        Usuario actualizado = usuarioService.updateUsuario(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // =================================================
    // ELIMINAR USUARIO
    // =================================================
    /**
     * Elimina un usuario por su ID.
     * 
     * @param id ID del usuario a eliminar
     * @return 204 No Content si se eliminó correctamente, 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        if (usuarioService.findById(id).isPresent()) {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
