package com.protectora.backend.controller;

import com.protectora.backend.model.Empleado;
import com.protectora.backend.services.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<Empleado> getAllEmpleados() {
        return empleadoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Integer id) {
        return empleadoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/veterinarios")
    public List<Empleado> getVeterinarios() {
        return empleadoService.findVeterinarios();
    }

    @PostMapping
    public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody Empleado empleado) {
        Empleado saved = empleadoService.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> updateEmpleado(
            @PathVariable Integer id,
            @Valid @RequestBody Empleado empleadoDetails) {

        return empleadoService.findById(id)
                .map(empleado -> {
                    empleado.setUsuario(empleadoDetails.getUsuario());
                    empleado.setRol(empleadoDetails.getRol());
                    empleado.setSalario(empleadoDetails.getSalario());
                    empleado.setHorario(empleadoDetails.getHorario());
                    empleado.setFechaAlta(empleadoDetails.getFechaAlta());
                    Empleado updated = empleadoService.save(empleado);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer id) {
        if (empleadoService.findById(id).isPresent()) {
            empleadoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
