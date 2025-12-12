package com.protectora.backend.controller;

import com.protectora.backend.dto.AdopcionDto;
import com.protectora.backend.model.Adopcion;
import com.protectora.backend.model.Usuario;
import com.protectora.backend.services.AdopcionService;
import com.protectora.backend.services.UsuarioService;
import com.protectora.backend.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de adopciones.
 * Maneja operaciones CRUD y consultas específicas como adopciones de un
 * usuario,
 * adopciones pendientes o aceptadas.
 */
@RestController
@RequestMapping("/api/adopciones")
public class AdopcionController {

    private final AdopcionService adopcionService;
    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;

    // Constructor con inyección de dependencias
    public AdopcionController(AdopcionService adopcionService,
            UsuarioService usuarioService,
            JwtTokenProvider jwtTokenProvider) {
        this.adopcionService = adopcionService;
        this.usuarioService = usuarioService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // =================================================
    // OBTENER ADOPCIONES
    // =================================================

    /**
     * Obtener todas las adopciones.
     * 
     * @return lista de AdopcionDto
     */
    @GetMapping
    public ResponseEntity<List<AdopcionDto>> getAll() {
        List<AdopcionDto> adopciones = adopcionService.findAllDTO();
        return ResponseEntity.ok(adopciones);
    }

    /**
     * Obtener adopción por ID.
     * 
     * @param id id de la adopción
     * @return adopción encontrada o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdopcionDto> getById(@PathVariable Integer id) {
        try {
            Adopcion adopcion = adopcionService.getById(id);
            return ResponseEntity.ok(AdopcionDto.fromEntity(adopcion));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener adopciones de un usuario específico.
     * 
     * @param idUsuario id del usuario
     * @return lista de adopciones o 204 si no tiene
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<AdopcionDto>> getByUsuario(@PathVariable Integer idUsuario) {
        List<AdopcionDto> adopciones = adopcionService.findByUsuarioDTO(idUsuario);
        if (adopciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(adopciones);
    }

    /**
     * Obtener adopciones del usuario autenticado mediante token JWT.
     * 
     * @param authHeader token JWT en el header Authorization
     * @return lista de adopciones del usuario o 401 si no autorizado
     */
    @GetMapping("/mis-adopciones")
    public ResponseEntity<List<AdopcionDto>> getMisAdopciones(@RequestHeader("Authorization") String authHeader) {
        // Validación del token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        Integer idUsuario = jwtTokenProvider.getUserIdFromToken(token);

        Usuario usuario = usuarioService.findById(idUsuario).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<AdopcionDto> adopciones = adopcionService.findByUsuarioDTO(usuario.getIdUsuario());
        if (adopciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(adopciones);
    }

    // =================================================
    // CREAR ADOPCION
    // =================================================

    /**
     * Crear una nueva adopción.
     * 
     * @param dto DTO con la información de la adopción
     * @return adopción creada o 400 si hay error
     */
    @PostMapping
    public ResponseEntity<AdopcionDto> create(@RequestBody AdopcionDto dto) {
        try {
            Adopcion guardada = adopcionService.create(dto.idUsuario(), dto.idMascota(), dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(AdopcionDto.fromEntity(guardada));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // =================================================
    // ACTUALIZAR ADOPCION
    // =================================================

    /**
     * Actualizar adopción existente.
     * 
     * @param id  id de la adopción
     * @param dto DTO con datos actualizados
     * @return adopción actualizada o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdopcionDto> update(@PathVariable Integer id, @RequestBody AdopcionDto dto) {
        try {
            Adopcion updated = adopcionService.update(id, dto);
            return ResponseEntity.ok(AdopcionDto.fromEntity(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // =================================================
    // ELIMINAR ADOPCION
    // =================================================

    /**
     * Eliminar adopción por ID.
     * 
     * @param id id de la adopción
     * @return 204 si eliminado o 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            adopcionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // =================================================
    // CONSULTAS ESPECIALES
    // =================================================

    /**
     * Obtener adopciones pendientes.
     */
    @GetMapping("/pendientes")
    public List<AdopcionDto> obtenerPendientes() {
        return adopcionService.obtenerPendientes();
    }

    /**
     * Cambiar el estado de una adopción.
     * 
     * @param id          id de la adopción
     * @param nuevoEstado nuevo estado como string (por ejemplo, PENDIENTE,
     *                    ACEPTADA)
     */
    @PutMapping("/{id}/estado")
    public void cambiarEstado(@PathVariable Integer id, @RequestParam String nuevoEstado) {
        adopcionService.cambiarEstado(id, Adopcion.Estado.valueOf(nuevoEstado));
    }

    /**
     * Obtener todas las adopciones sin filtro.
     */
    @GetMapping("/todas")
    public ResponseEntity<List<AdopcionDto>> getTodasAdopciones() {
        return ResponseEntity.ok(adopcionService.getTodasAdopciones());
    }

    /**
     * Obtener adopciones aceptadas.
     */
    @GetMapping("/aceptadas")
    public ResponseEntity<List<AdopcionDto>> obtenerAceptadas() {
        return ResponseEntity.ok(adopcionService.obtenerAceptadas());
    }
}
