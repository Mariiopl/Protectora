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

/**
 * Controlador REST para la gestión de mascotas.
 * Permite operaciones CRUD, subir imágenes, y obtener listas de mascotas
 * adoptables o adoptadas.
 */
@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // =================================================
    // OBTENER TODAS LAS MASCOTAS
    // =================================================
    /**
     * Devuelve todas las mascotas registradas en la base de datos.
     * 
     * @return Lista de mascotas o error en caso de fallo
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Mascota> mascotas = mascotaService.getAll();
            return ResponseEntity.ok(mascotas);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    // =================================================
    // OBTENER MASCOTA POR ID
    // =================================================
    /**
     * Obtiene una mascota específica por su ID.
     * 
     * @param id ID de la mascota
     * @return Mascota encontrada o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        try {
            Mascota mascota = mascotaService.getById(id);
            return ResponseEntity.ok(mascota);
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
    }

    // =================================================
    // CREAR NUEVA MASCOTA
    // =================================================
    /**
     * Crea una nueva mascota. Permite subir opcionalmente una imagen.
     * 
     * @param mascotaDto Datos de la mascota (DTO)
     * @param foto       Imagen de la mascota (opcional)
     * @return Mensaje de éxito o error
     */
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

    // =================================================
    // ACTUALIZAR MASCOTA EXISTENTE
    // =================================================
    /**
     * Actualiza una mascota existente. Permite actualizar los datos y cambiar la
     * imagen.
     * 
     * @param id         ID de la mascota a actualizar
     * @param mascotaDto Datos actualizados (DTO)
     * @param foto       Nueva imagen de la mascota (opcional)
     * @return Mensaje de éxito o 404 si no se encuentra
     */
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

    // =================================================
    // ELIMINAR MASCOTA
    // =================================================
    /**
     * Elimina una mascota por su ID.
     * 
     * @param id ID de la mascota
     * @return Mensaje de éxito o 404 si no se encuentra
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            String mensaje = mascotaService.delete(id);
            return ResponseEntity.ok(Map.of("message", mensaje));
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
    }

    // =================================================
    // OBTENER IMAGEN DE UNA MASCOTA
    // =================================================
    /**
     * Devuelve la imagen de una mascota por nombre de archivo.
     * 
     * @param filename Nombre del archivo de la imagen
     * @return Imagen como Resource o 404 si no se encuentra
     */
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

    // =================================================
    // OBTENER MASCOTAS ADOPTABLES
    // =================================================
    /**
     * Devuelve todas las mascotas que actualmente están en adopción.
     * 
     * @return Lista de mascotas adoptables o error
     */
    @GetMapping("/adoptables")
    public ResponseEntity<?> getAdoptable() {
        try {
            List<Mascota> adoptable = mascotaService.getAdoptable();
            return ResponseEntity.ok(adoptable);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    // =================================================
    // OBTENER MASCOTAS ADOPTADAS
    // =================================================
    /**
     * Devuelve todas las mascotas que ya han sido adoptadas.
     * 
     * @return Lista de mascotas adoptadas o error
     */
    @GetMapping("/adoptadas")
    public ResponseEntity<?> getAdoptado() {
        try {
            List<Mascota> adoptadas = mascotaService.getAdoptado();
            return ResponseEntity.ok(adoptadas);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }
}
