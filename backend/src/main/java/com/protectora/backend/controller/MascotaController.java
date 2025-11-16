package com.protectora.backend.controller;

import com.protectora.backend.dto.MascotaDto;
import com.protectora.backend.model.Mascota;
import com.protectora.backend.services.MascotaService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Mascota> mascotas = mascotaService.getAll();
            return ResponseEntity.ok(mascotas);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Mascota mascota = mascotaService.getById(id);
            return ResponseEntity.ok(mascota);
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<?> create(
            @RequestPart("mascota") @Valid MascotaDto mascotaDto,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            String mensaje = mascotaService.create(mascotaDto, foto);
            return ResponseEntity.ok(Map.of("message", mensaje));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestPart("mascota") @Valid MascotaDto mascotaDto,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {
        try {
            String mensaje = mascotaService.update(id, mascotaDto, foto);
            return ResponseEntity.ok(Map.of("message", mensaje));
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            String mensaje = mascotaService.delete(id);
            return ResponseEntity.ok(Map.of("message", mensaje));
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping("/imagenes/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Resource image = mascotaService.getImage(filename);
            String contentType = Files.probeContentType(Paths.get(image.getURI()));
            return ResponseEntity.ok()
                    .contentType(contentType != null ? MediaType.parseMediaType(contentType)
                            : MediaType.APPLICATION_OCTET_STREAM)
                    .body(image);
        } catch (IOException ex) {
            return ResponseEntity.status(404).body(null);
        }
    }

}
