package com.protectora.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.protectora.backend.model.Mascota;
import com.protectora.backend.services.MascotaService;
import com.protectora.backend.services.FotoMascotaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;
    private final FotoMascotaService fotoMascotaService;

    public MascotaController(MascotaService mascotaService,
            FotoMascotaService fotoMascotaService) {
        this.mascotaService = mascotaService;
        this.fotoMascotaService = fotoMascotaService;
    }

    @GetMapping
    public List<Mascota> getAllMascotas() {
        return mascotaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mascota> getMascotaById(@PathVariable Integer id) {
        return mascotaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Mascota> createMascota(
            @RequestPart("mascota") @Valid Mascota mascota,
            @RequestPart(value = "foto", required = false) MultipartFile foto) {

        Mascota savedMascota = mascotaService.save(mascota);

        if (foto != null && !foto.isEmpty()) {
            fotoMascotaService.guardarFoto(savedMascota, foto);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMascota);
    }

    @PutMapping("/{id}")
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

            Mascota updated = mascotaService.save(mascota);

            if (foto != null && !foto.isEmpty()) {
                fotoMascotaService.guardarFoto(updated, foto);
            }

            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMascota(@PathVariable Integer id) {
        if (mascotaService.findById(id).isPresent()) {
            mascotaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
