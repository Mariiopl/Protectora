package com.protectora.backend.controller;

import com.protectora.backend.model.Apadrinamiento;
import com.protectora.backend.services.ApadrinamientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apadrinamientos")
@CrossOrigin(origins = "*")
public class ApadrinamientoController {

    private final ApadrinamientoService apadrinamientoService;

    public ApadrinamientoController(ApadrinamientoService apadrinamientoService) {
        this.apadrinamientoService = apadrinamientoService;
    }

    @GetMapping
    public List<Apadrinamiento> getAllApadrinamientos() {
        return apadrinamientoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apadrinamiento> getApadrinamientoById(@PathVariable Integer id) {
        return apadrinamientoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Apadrinamiento> createApadrinamiento(@Valid @RequestBody Apadrinamiento apadrinamiento) {
        Apadrinamiento saved = apadrinamientoService.save(apadrinamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apadrinamiento> updateApadrinamiento(
            @PathVariable Integer id,
            @Valid @RequestBody Apadrinamiento apadrinamientoDetails) {

        return apadrinamientoService.findById(id)
                .map(apadrinamiento -> {
                    apadrinamiento.setUsuario(apadrinamientoDetails.getUsuario());
                    apadrinamiento.setMascota(apadrinamientoDetails.getMascota());
                    apadrinamiento.setFechaInicio(apadrinamientoDetails.getFechaInicio());
                    apadrinamiento.setFechaFin(apadrinamientoDetails.getFechaFin());
                    apadrinamiento.setEstado(apadrinamientoDetails.getEstado());
                    Apadrinamiento updated = apadrinamientoService.save(apadrinamiento);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApadrinamiento(@PathVariable Integer id) {
        if (apadrinamientoService.findById(id).isPresent()) {
            apadrinamientoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
