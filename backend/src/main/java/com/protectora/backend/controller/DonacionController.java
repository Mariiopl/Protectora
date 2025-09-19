package com.protectora.backend.controller;

import com.protectora.backend.model.Donacion;
import com.protectora.backend.services.DonacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@CrossOrigin(origins = "*")
public class DonacionController {

    private final DonacionService donacionService;

    public DonacionController(DonacionService donacionService) {
        this.donacionService = donacionService;
    }

    @GetMapping
    public List<Donacion> getAllDonaciones() {
        return donacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donacion> getDonacionById(@PathVariable Integer id) {
        return donacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Donacion> createDonacion(@Valid @RequestBody Donacion donacion) {
        Donacion saved = donacionService.save(donacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donacion> updateDonacion(
            @PathVariable Integer id,
            @Valid @RequestBody Donacion donacionDetails) {

        return donacionService.findById(id)
                .map(donacion -> {
                    donacion.setUsuario(donacionDetails.getUsuario());
                    donacion.setCantidad(donacionDetails.getCantidad());
                    donacion.setFecha(donacionDetails.getFecha());
                    donacion.setTipo(donacionDetails.getTipo());
                    donacion.setMetodoPago(donacionDetails.getMetodoPago());
                    Donacion updated = donacionService.save(donacion);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonacion(@PathVariable Integer id) {
        if (donacionService.findById(id).isPresent()) {
            donacionService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
