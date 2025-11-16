package com.protectora.backend.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.protectora.backend.model.Mascota;
import com.protectora.backend.services.MascotaService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    // Carpeta donde guardamos las fotos
    private final String uploadDir = "I:/Protectora/frontend/src/assets/mascotas/";

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
        new File(uploadDir).mkdirs();
    }

    /** 🔹 Obtener todas las mascotas */
    @GetMapping
    public List<Mascota> getAllMascotas() {
        return mascotaService.findAll();
    }

    /** 🔹 Obtener mascota por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Integer id) {
        return mascotaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /** 🔹 Crear mascota con foto opcional */
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Mascota> createMascota(
            @RequestPart("mascota") @Valid Mascota mascota,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {

        Mascota savedMascota = mascotaService.save(mascota);

        if (foto != null && !foto.isEmpty()) {
            String nombreArchivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
            File destino = new File(uploadDir + nombreArchivo);
            try {
                foto.transferTo(destino);
                savedMascota.setFoto(nombreArchivo);
                savedMascota = mascotaService.save(savedMascota);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen", e);
            }
        }

        return ResponseEntity.status(201).body(savedMascota);
    }

    /** 🔹 Actualizar mascota con foto opcional */
    @PutMapping(value = "/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<Mascota> updateMascota(
            @PathVariable Integer id,
            @RequestPart("mascota") @Valid Mascota mascotaDetails,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {

        return mascotaService.findById(id).map(mascota -> {

            mascota.setNombre(mascotaDetails.getNombre());
            mascota.setEspecie(mascotaDetails.getEspecie());
            mascota.setRaza(mascotaDetails.getRaza());
            mascota.setEdad(mascotaDetails.getEdad());
            mascota.setTamano(mascotaDetails.getTamano());
            mascota.setSexo(mascotaDetails.getSexo());
            mascota.setCaracter(mascotaDetails.getCaracter());
            mascota.setNecesidadesEspeciales(mascotaDetails.getNecesidadesEspeciales());
            mascota.setEsterilizado(mascotaDetails.getEsterilizado());
            mascota.setVacunado(mascotaDetails.getVacunado());
            mascota.setDesparasitado(mascotaDetails.getDesparasitado());
            mascota.setEstadoAdopcion(mascotaDetails.getEstadoAdopcion());
            mascota.setHistoria(mascotaDetails.getHistoria());
            mascota.setUbicacion(mascotaDetails.getUbicacion());
            mascota.setFechaIngreso(mascotaDetails.getFechaIngreso());

            if (foto != null && !foto.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
                File destino = new File(uploadDir + nombreArchivo);
                try {
                    foto.transferTo(destino);
                    mascota.setFoto(nombreArchivo);
                } catch (IOException e) {
                    throw new RuntimeException("Error al subir la foto", e);
                }
            }

            Mascota updated = mascotaService.save(mascota);
            return ResponseEntity.ok(updated);

        }).orElse(ResponseEntity.notFound().build());
    }

    /** 🔹 Eliminar mascota */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Integer id) {
        if (mascotaService.findById(id).isPresent()) {
            mascotaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /** 🔹 Servir fotos de mascotas */
    @GetMapping("/imagenes/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path file = Paths.get(uploadDir).resolve(filename);
        if (!Files.exists(file)) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new UrlResource(file.toUri());
        String contentType = Files.probeContentType(file);
        return ResponseEntity.ok()
                .contentType(contentType != null ? MediaType.parseMediaType(contentType)
                        : MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
