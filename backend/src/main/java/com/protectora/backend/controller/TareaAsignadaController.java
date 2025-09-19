package com.protectora.backend.controller;

import com.protectora.backend.model.TareaAsignada;
import com.protectora.backend.services.TareaAsignadaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas-asignadas")
@CrossOrigin(origins = "*")
public class TareaAsignadaController {

    private final TareaAsignadaService tareaAsignadaService;

    public TareaAsignadaController(TareaAsignadaService tareaAsignadaService) {
        this.tareaAsignadaService = tareaAsignadaService;
    }

    @GetMapping
    public List<TareaAsignada> getAllTareasAsignadas() {
        return tareaAsignadaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaAsignada> getTareaAsignadaById(@PathVariable Integer id) {
        return tareaAsignadaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TareaAsignada> createTareaAsignada(@Valid @RequestBody TareaAsignada tareaAsignada) {
        TareaAsignada saved = tareaAsignadaService.save(tareaAsignada);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaAsignada> updateTareaAsignada(
            @PathVariable Integer id,
            @Valid @RequestBody TareaAsignada tareaAsignadaDetails) {

        return tareaAsignadaService.findById(id)
                .map(tarea -> {
                    tarea.setTarea(tareaAsignadaDetails.getTarea());
                    tarea.setEmpleado(tareaAsignadaDetails.getEmpleado());
                    tarea.setObservaciones(tareaAsignadaDetails.getObservaciones());
                    TareaAsignada updated = tareaAsignadaService.save(tarea);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTareaAsignada(@PathVariable Integer id) {
        if (tareaAsignadaService.findById(id).isPresent()) {
            tareaAsignadaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
