package com.protectora.backend.controller;

import com.protectora.backend.model.Adopcion;
import com.protectora.backend.services.AdopcionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adopciones")
@CrossOrigin(origins = "*")
public class AdopcionController {

    private final AdopcionService adopcionService;

    public AdopcionController(AdopcionService adopcionService) {
        this.adopcionService = adopcionService;
    }

    @GetMapping
    public List<Adopcion> getAllAdopciones() {
        return adopcionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Adopcion> getAdopcionById(@PathVariable Integer id) {
        return adopcionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Adopcion> createAdopcion(@Valid @RequestBody Adopcion adopcion) {
        Adopcion saved = adopcionService.save(adopcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Adopcion> updateAdopcion(
            @PathVariable Integer id,
            @Valid @RequestBody Adopcion adopcionDetails) {

        return adopcionService.findById(id)
                .map(adopcion -> {
                    adopcion.setUsuario(adopcionDetails.getUsuario());
                    adopcion.setMascota(adopcionDetails.getMascota());
                    adopcion.setEstado(adopcionDetails.getEstado());
                    adopcion.setFechaSolicitud(adopcionDetails.getFechaSolicitud());
                    adopcion.setFechaAdopcion(adopcionDetails.getFechaAdopcion());
                    adopcion.setExperiencia(adopcionDetails.getExperiencia());
                    adopcion.setTipoVivienda(adopcionDetails.getTipoVivienda());
                    adopcion.setComentarios(adopcionDetails.getComentarios());
                    Adopcion updated = adopcionService.save(adopcion);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdopcion(@PathVariable Integer id) {
        if (adopcionService.findById(id).isPresent()) {
            adopcionService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
