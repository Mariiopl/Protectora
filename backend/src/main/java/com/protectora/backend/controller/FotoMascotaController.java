package com.protectora.backend.controller;

import com.protectora.backend.model.FotoMascota;
import com.protectora.backend.services.FotoMascotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fotos-mascotas")
@CrossOrigin(origins = "*")
public class FotoMascotaController {

    private final FotoMascotaService fotoMascotaService;

    public FotoMascotaController(FotoMascotaService fotoMascotaService) {
        this.fotoMascotaService = fotoMascotaService;
    }

    @GetMapping
    public List<FotoMascota> getAllFotosMascotas() {
        return fotoMascotaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FotoMascota> getFotoMascotaById(@PathVariable Integer id) {
        return fotoMascotaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FotoMascota> createFotoMascota(@Valid @RequestBody FotoMascota fotoMascota) {
        FotoMascota saved = fotoMascotaService.save(fotoMascota);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FotoMascota> updateFotoMascota(
            @PathVariable Integer id,
            @Valid @RequestBody FotoMascota fotoMascotaDetails) {

        return fotoMascotaService.findById(id)
                .map(foto -> {
                    foto.setMascota(fotoMascotaDetails.getMascota());
                    foto.setUrlImagen(fotoMascotaDetails.getUrlImagen());
                    FotoMascota updated = fotoMascotaService.save(foto);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFotoMascota(@PathVariable Integer id) {
        if (fotoMascotaService.findById(id).isPresent()) {
            fotoMascotaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
