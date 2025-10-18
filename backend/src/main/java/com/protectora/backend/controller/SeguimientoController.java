package com.protectora.backend.controller;

import com.protectora.backend.model.Seguimiento;
import com.protectora.backend.services.SeguimientoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    public SeguimientoController(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    @GetMapping
    public List<Seguimiento> getAllSeguimientos() {
        return seguimientoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> getSeguimientoById(@PathVariable Integer id) {
        return seguimientoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Seguimiento> createSeguimiento(@Valid @RequestBody Seguimiento seguimiento) {
        Seguimiento saved = seguimientoService.save(seguimiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seguimiento> updateSeguimiento(
            @PathVariable Integer id,
            @Valid @RequestBody Seguimiento seguimientoDetails) {

        return seguimientoService.findById(id)
                .map(seg -> {
                    seg.setAdopcion(seguimientoDetails.getAdopcion());
                    seg.setFecha(seguimientoDetails.getFecha());
                    seg.setMensaje(seguimientoDetails.getMensaje());
                    seg.setUrlImagen(seguimientoDetails.getUrlImagen());
                    seg.setUrlVideo(seguimientoDetails.getUrlVideo());
                    seg.setTipo(seguimientoDetails.getTipo());
                    seg.setEnlaceVideollamada(seguimientoDetails.getEnlaceVideollamada());
                    Seguimiento updated = seguimientoService.save(seg);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeguimiento(@PathVariable Integer id) {
        if (seguimientoService.findById(id).isPresent()) {
            seguimientoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
