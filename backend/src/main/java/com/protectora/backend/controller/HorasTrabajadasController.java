package com.protectora.backend.controller;

import com.protectora.backend.model.HorasTrabajadas;
import com.protectora.backend.services.HorasTrabajadasService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horas-trabajadas")
public class HorasTrabajadasController {

    private final HorasTrabajadasService horasTrabajadasService;

    public HorasTrabajadasController(HorasTrabajadasService horasTrabajadasService) {
        this.horasTrabajadasService = horasTrabajadasService;
    }

    @GetMapping
    public List<HorasTrabajadas> getAllHorasTrabajadas() {
        return horasTrabajadasService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorasTrabajadas> getHorasTrabajadasById(@PathVariable Integer id) {
        return horasTrabajadasService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HorasTrabajadas> createHorasTrabajadas(@Valid @RequestBody HorasTrabajadas horasTrabajadas) {
        HorasTrabajadas saved = horasTrabajadasService.save(horasTrabajadas);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorasTrabajadas> updateHorasTrabajadas(
            @PathVariable Integer id,
            @Valid @RequestBody HorasTrabajadas horasTrabajadasDetails) {

        return horasTrabajadasService.findById(id)
                .map(horas -> {
                    horas.setEmpleado(horasTrabajadasDetails.getEmpleado());
                    horas.setFecha(horasTrabajadasDetails.getFecha());
                    horas.setHoras(horasTrabajadasDetails.getHoras());
                    HorasTrabajadas updated = horasTrabajadasService.save(horas);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorasTrabajadas(@PathVariable Integer id) {
        if (horasTrabajadasService.findById(id).isPresent()) {
            horasTrabajadasService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
