package com.protectora.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.protectora.backend.model.Mascota;
import com.protectora.backend.services.MascotaService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/mascotas")

public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // Obtener todas las mascotas
    @GetMapping
    public List<Mascota> getAllMascotas() {
        return mascotaService.findAll();
    }

    // Obtener mascota por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Integer id) {
        return mascotaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nueva mascota con validación
    @PostMapping
    public ResponseEntity<Mascota> createMascota(@Valid @RequestBody Mascota mascota) {
        Mascota saved = mascotaService.save(mascota);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ACtualizar mascota existente
    @PutMapping("/{id}")
    public ResponseEntity<Mascota> updateMascota(
            @PathVariable Integer id,
            @Valid @RequestBody Mascota mascotaDetails) {

        return mascotaService.findById(id)
                .map(mascota -> {
                    mascota.setNombre(mascotaDetails.getNombre());
                    mascota.setEspecie(mascotaDetails.getEspecie());
                    mascota.setRaza(mascotaDetails.getRaza());
                    mascota.setEdad(mascotaDetails.getEdad());
                    mascota.setTamaño(mascotaDetails.getTamaño());
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
                    Mascota updated = mascotaService.save(mascota);
                    return ResponseEntity.ok(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar mascota por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Integer id) {
        if (mascotaService.findById(id).isPresent()) {
            mascotaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
