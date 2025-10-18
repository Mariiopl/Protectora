package com.protectora.backend.controller;

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
    public List<Tratamiento> getAllTratamientos() {
        return tratamientoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tratamiento> getTratamientoById(@PathVariable Integer id) {
        return tratamientoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tratamiento> createTratamiento(@Valid @RequestBody Tratamiento tratamiento) {
        Tratamiento saved = tratamientoService.save(tratamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tratamiento> updateTratamiento(
            @PathVariable Integer id,
            @Valid @RequestBody Tratamiento tratamientoDetails) {

        return tratamientoService.findById(id)
                .map(trat -> {
                    trat.setMascota(tratamientoDetails.getMascota());
                    trat.setTipo(tratamientoDetails.getTipo());
                    trat.setDescripcion(tratamientoDetails.getDescripcion());
                    trat.setFecha(tratamientoDetails.getFecha());
                    trat.setVeterinario(tratamientoDetails.getVeterinario());
                    Tratamiento updated = tratamientoService.save(trat);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTratamiento(@PathVariable Integer id) {
        if (tratamientoService.findById(id).isPresent()) {
            tratamientoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
