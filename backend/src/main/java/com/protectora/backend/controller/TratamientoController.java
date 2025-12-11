package com.protectora.backend.controller;

import com.protectora.backend.dto.TratamientoDto;
import com.protectora.backend.model.Tratamiento;
import com.protectora.backend.services.TratamientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
public class TratamientoController {

    private final TratamientoService tratamientoService;

    public TratamientoController(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }

    @GetMapping
    public ResponseEntity<List<Tratamiento>> getAllTratamientos() {
        return ResponseEntity.ok(tratamientoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tratamiento> getTratamientoById(@PathVariable Integer id) {
        return tratamientoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tratamiento> createTratamiento(@Valid @RequestBody TratamientoDto dto) {
        Tratamiento saved = tratamientoService.saveFromDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tratamiento> updateTratamiento(
            @PathVariable Integer id,
            @Valid @RequestBody TratamientoDto dto) {
        Tratamiento updated = tratamientoService.updateFromDTO(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTratamiento(@PathVariable Integer id) {
        tratamientoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<Tratamiento>> getTratamientosPorMascota(@PathVariable Integer idMascota) {
        List<Tratamiento> tratamientos = tratamientoService.findByMascotaId(idMascota);
        return ResponseEntity.ok(tratamientos);
    }

    @PutMapping("/{id}/informado")
    public ResponseEntity<Tratamiento> marcarInformado(@PathVariable Integer id) {
        Tratamiento updated = tratamientoService.marcarInformado(id);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/realizado")
    public ResponseEntity<Tratamiento> marcarRealizado(@PathVariable Integer id) {
        Tratamiento t = tratamientoService.marcarRealizado(id);
        return ResponseEntity.ok(t);
    }
}
