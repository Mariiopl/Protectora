package com.protectora.backend.controller;

import com.protectora.backend.dto.TratamientoDto;
import com.protectora.backend.model.Tratamiento;
import com.protectora.backend.services.TratamientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de tratamientos de mascotas.
 * Permite crear, listar, actualizar, eliminar y cambiar el estado de
 * tratamientos.
 */
@RestController
@RequestMapping("/api/tratamientos")
@CrossOrigin(origins = "http://localhost:4200") // Permitimos llamadas desde Angular
public class TratamientoController {

    private final TratamientoService tratamientoService;

    public TratamientoController(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    // =================================================
    // LISTAR TODOS LOS TRATAMIENTOS
    // =================================================
    @GetMapping
    public ResponseEntity<List<Tratamiento>> getAllTratamientos() {
        return ResponseEntity.ok(tratamientoService.findAll());
    }

    // =================================================
    // OBTENER UN TRATAMIENTO POR ID
    // =================================================
    @GetMapping("/{id}")
    public ResponseEntity<Tratamiento> getTratamientoById(@PathVariable Integer id) {
        return tratamientoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =================================================
    // CREAR TRATAMIENTO
    // =================================================
    @PostMapping
    public ResponseEntity<Tratamiento> createTratamiento(@Valid @RequestBody TratamientoDto dto) {
        Tratamiento saved = tratamientoService.saveFromDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // =================================================
    // ACTUALIZAR TRATAMIENTO
    // =================================================
    @PutMapping("/{id}")
    public ResponseEntity<Tratamiento> updateTratamiento(
            @PathVariable Integer id,
            @Valid @RequestBody TratamientoDto dto) {
        Tratamiento updated = tratamientoService.updateFromDTO(id, dto);
        return ResponseEntity.ok(updated);
    }

    // =================================================
    // ELIMINAR TRATAMIENTO
    // =================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTratamiento(@PathVariable Integer id) {
        tratamientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // =================================================
    // LISTAR TRATAMIENTOS POR MASCOTA
    // =================================================
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<Tratamiento>> getTratamientosPorMascota(@PathVariable Integer idMascota) {
        List<Tratamiento> tratamientos = tratamientoService.findByMascotaId(idMascota);
        return ResponseEntity.ok(tratamientos);
    }

    // =================================================
    // MARCAR TRATAMIENTO COMO INFORMADO
    // =================================================
    /**
     * Cambia el estado del tratamiento a "informado".
     * 
     * @param id ID del tratamiento
     * @return Tratamiento actualizado
     */
    @PutMapping("/{id}/informado")
    public ResponseEntity<Tratamiento> marcarInformado(@PathVariable Integer id) {
        Tratamiento updated = tratamientoService.marcarInformado(id);
        return ResponseEntity.ok(updated);
    }

    // =================================================
    // MARCAR TRATAMIENTO COMO REALIZADO
    // =================================================
    /**
     * Cambia el estado del tratamiento a "realizado".
     * 
     * @param id ID del tratamiento
     * @return Tratamiento actualizado
     */
    @PutMapping("/{id}/realizado")
    public ResponseEntity<Tratamiento> marcarRealizado(@PathVariable Integer id) {
        Tratamiento t = tratamientoService.marcarRealizado(id);
        return ResponseEntity.ok(t);
    }
}
