package com.protectora.backend.controller;

import com.protectora.backend.model.Turno;
import com.protectora.backend.services.TurnoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@CrossOrigin(origins = "*")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @GetMapping
    public List<Turno> getAllTurnos() {
        return turnoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> getTurnoById(@PathVariable Integer id) {
        return turnoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Turno> createTurno(@Valid @RequestBody Turno turno) {
        Turno saved = turnoService.save(turno);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turno> updateTurno(
            @PathVariable Integer id,
            @Valid @RequestBody Turno turnoDetails) {

        return turnoService.findById(id)
                .map(turno -> {
                    turno.setEmpleado(turnoDetails.getEmpleado());
                    turno.setFecha(turnoDetails.getFecha());
                    turno.setHoraInicio(turnoDetails.getHoraInicio());
                    turno.setHoraFin(turnoDetails.getHoraFin());
                    turno.setEstado(turnoDetails.getEstado());
                    Turno updated = turnoService.save(turno);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable Integer id) {
        if (turnoService.findById(id).isPresent()) {
            turnoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
