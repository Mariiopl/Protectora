package com.protectora.backend.controller;

import com.protectora.backend.model.Tarea;
import com.protectora.backend.services.TareaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public List<Tarea> getAllTareas() {
        return tareaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> getTareaById(@PathVariable Integer id) {
        return tareaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tarea> createTarea(@Valid @RequestBody Tarea tarea) {
        Tarea saved = tareaService.save(tarea);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> updateTarea(
            @PathVariable Integer id,
            @Valid @RequestBody Tarea tareaDetails) {

        return tareaService.findById(id)
                .map(tarea -> {
                    tarea.setTipo(tareaDetails.getTipo());
                    tarea.setDescripcion(tareaDetails.getDescripcion());
                    tarea.setFecha(tareaDetails.getFecha());
                    tarea.setEstado(tareaDetails.getEstado());
                    Tarea updated = tareaService.save(tarea);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarea(@PathVariable Integer id) {
        if (tareaService.findById(id).isPresent()) {
            tareaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
