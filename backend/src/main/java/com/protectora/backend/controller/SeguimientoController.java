package com.protectora.backend.controller;

import com.protectora.backend.dto.SeguimientoDto;
import com.protectora.backend.model.Adopcion;
import com.protectora.backend.model.Seguimiento;
import com.protectora.backend.repository.AdopcionRepository;
import com.protectora.backend.services.SeguimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controlador REST para la gestión de seguimientos de adopciones.
 * Permite crear, listar, subir fotos y marcar como completados los
 * seguimientos.
 */
@RestController
@RequestMapping("/api/seguimientos")
@CrossOrigin(origins = "http://localhost:4200") // Permite llamadas desde Angular
public class SeguimientoController {

    private final SeguimientoService seguimientoService;
    private final AdopcionRepository adopcionRepository;

    public SeguimientoController(SeguimientoService seguimientoService,
            AdopcionRepository adopcionRepository) {
        this.seguimientoService = seguimientoService;
        this.adopcionRepository = adopcionRepository;
    }

    // =================================================
    // LISTAR SEGUIMIENTOS POR ADOPCIÓN
    // =================================================
    /**
     * Obtiene los seguimientos asociados a una adopción específica.
     * 
     * @param id ID de la adopción
     * @return Lista de seguimientos o 204 si no hay ninguno
     */
    @GetMapping("/adopcion/{id}")
    public ResponseEntity<List<SeguimientoDto>> porAdopcion(@PathVariable Integer id) {
        List<SeguimientoDto> lista = seguimientoService.getByAdopcion(id);
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // =================================================
    // CREAR SEGUIMIENTO
    // =================================================
    /**
     * Crea un nuevo seguimiento para una adopción.
     * 
     * @param dto Datos del seguimiento
     * @return Seguimiento creado
     */
    @PostMapping
    public ResponseEntity<SeguimientoDto> crear(@RequestBody SeguimientoDto dto) {
        Adopcion adopcion = adopcionRepository.findById(dto.getIdAdopcion())
                .orElseThrow(() -> new RuntimeException("Adopción no encontrada"));

        Seguimiento s = Seguimiento.builder()
                .adopcion(adopcion)
                .fechaProgramada(dto.getFechaProgramada())
                .mensaje(dto.getMensaje())
                .estado(dto.getEstado() != null ? dto.getEstado() : Seguimiento.Estado.pendiente)
                .build();

        Seguimiento guardado = seguimientoService.save(s);
        return ResponseEntity.ok(SeguimientoDto.fromEntity(guardado));
    }

    // =================================================
    // SUBIR FOTO DE SEGUIMIENTO
    // =================================================
    /**
     * Permite subir una foto asociada a un seguimiento.
     * 
     * @param id   ID del seguimiento
     * @param file Archivo de imagen
     * @return URL de la foto subida o error
     */
    @PostMapping("/{id}/foto")
    public ResponseEntity<?> subirFoto(@PathVariable Integer id,
            @RequestParam("file") MultipartFile file) {
        try {
            Seguimiento s = seguimientoService.getById(id);
            if (s == null)
                return ResponseEntity.notFound().build();

            // Guardar la foto en servidor
            String url = seguimientoService.guardarFoto(file, id);
            s.setUrlImagen(url);
            seguimientoService.save(s);

            return ResponseEntity.ok(url);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error subiendo foto");
        }
    }

    // =================================================
    // MARCAR SEGUIMIENTO COMO COMPLETADO
    // =================================================
    /**
     * Marca un seguimiento como completado.
     * 
     * @param id ID del seguimiento
     * @return Mensaje de confirmación o 404 si no existe
     */
    @PatchMapping("/{id}/completar")
    public ResponseEntity<?> marcarCompletado(@PathVariable Integer id) {
        Seguimiento s = seguimientoService.getById(id);
        if (s == null)
            return ResponseEntity.notFound().build();

        s.setEstado(Seguimiento.Estado.completado);
        seguimientoService.save(s);

        return ResponseEntity.ok("Completado");
    }

    // =================================================
    // VER ARCHIVO DE SEGUIMIENTO
    // =================================================
    /**
     * Devuelve el archivo (imagen) de un seguimiento dado su nombre.
     * 
     * @param nombre Nombre del archivo
     * @return Bytes del archivo o 404 si no existe
     */
    @GetMapping("/archivo/{nombre}")
    public ResponseEntity<?> verArchivo(@PathVariable String nombre) {
        try {
            Path path = Paths.get("uploads/seguimientos/" + nombre);
            byte[] bytes = Files.readAllBytes(path);

            return ResponseEntity
                    .ok()
                    .header("Content-Type", Files.probeContentType(path))
                    .body(bytes);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
