package com.protectora.backend.controller;

import com.protectora.backend.dto.AdopcionDto;
import com.protectora.backend.model.Adopcion;
import com.protectora.backend.model.Usuario;
import com.protectora.backend.security.JwtTokenProvider;
import com.protectora.backend.services.AdopcionService;
import com.protectora.backend.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adopciones")
public class AdopcionController {

    private final AdopcionService adopcionService;
    private final UsuarioService usuarioService;
    private final JwtTokenProvider jwtTokenProvider;

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
    @GetMapping
    public ResponseEntity<List<AdopcionDto>> getAll() {
        List<AdopcionDto> adopciones = adopcionService.findAllDTO();
        return ResponseEntity.ok(adopciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdopcionDto> getById(@PathVariable Integer id) {
        try {
            Adopcion adopcion = adopcionService.getById(id);
            return ResponseEntity.ok(AdopcionDto.fromEntity(adopcion));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<AdopcionDto>> getByUsuario(@PathVariable Integer idUsuario) {
        List<AdopcionDto> adopciones = adopcionService.findByUsuarioDTO(idUsuario);
        if (adopciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(adopciones);
    }

    @GetMapping("/mis-adopciones")
    public ResponseEntity<List<AdopcionDto>> getMisAdopciones(@RequestHeader("Authorization") String authHeader) {
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            adopcionService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
