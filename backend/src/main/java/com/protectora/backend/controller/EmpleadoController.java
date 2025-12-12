package com.protectora.backend.controller;

import com.protectora.backend.model.Empleado;
import com.protectora.backend.services.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de empleados.
 * Permite operaciones CRUD completas: crear, leer, actualizar y eliminar
 * empleados,
 * además de listar solo los empleados que sean veterinarios.
 */
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    // Constructor con inyección de dependencia del servicio de empleados
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    // =================================================
    // OBTENER TODOS LOS EMPLEADOS
    // =================================================
    /**
     * Devuelve la lista completa de empleados.
     * 
     * @return lista de empleados
     */
    @GetMapping
    public List<Empleado> getAllEmpleados() {
        return empleadoService.findAll();
    }

    // =================================================
    // OBTENER EMPLEADO POR ID
    // =================================================
    /**
     * Obtiene un empleado específico por su ID.
     * 
     * @param id ID del empleado
     * @return Empleado encontrado o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Integer id) {
        return empleadoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // =================================================
    // OBTENER SOLO VETERINARIOS
    // =================================================
    /**
     * Devuelve únicamente los empleados que tengan rol de veterinario.
     * 
     * @return lista de empleados veterinarios
     */
    @GetMapping("/veterinarios")
    public List<Empleado> getVeterinarios() {
        return empleadoService.findVeterinarios();
    }

    // =================================================
    // CREAR NUEVO EMPLEADO
    // =================================================
    /**
     * Crea un nuevo empleado en el sistema.
     * 
     * @param empleado Objeto empleado enviado en el cuerpo de la petición
     * @return Empleado creado con status 201
     */
    @PostMapping
    public ResponseEntity<Empleado> createEmpleado(@Valid @RequestBody Empleado empleado) {
        Empleado saved = empleadoService.save(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // =================================================
    // ACTUALIZAR EMPLEADO EXISTENTE
    // =================================================
    /**
     * Actualiza un empleado existente.
     * 
     * @param id              ID del empleado a actualizar
     * @param empleadoDetails Datos actualizados del empleado
     * @return Empleado actualizado o 404 si no se encuentra
     */
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> updateEmpleado(
            @PathVariable Integer id,
            @Valid @RequestBody Empleado empleadoDetails) {

        return empleadoService.findById(id)
                .map(empleado -> {
                    // Actualización de campos permitidos
                    empleado.setUsuario(empleadoDetails.getUsuario());
                    empleado.setRol(empleadoDetails.getRol());
                    empleado.setSalario(empleadoDetails.getSalario());
                    empleado.setHorario(empleadoDetails.getHorario());
                    empleado.setFechaAlta(empleadoDetails.getFechaAlta());

                    // Guardar cambios
                    Empleado updated = empleadoService.save(empleado);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // =================================================
    // ELIMINAR EMPLEADO
    // =================================================
    /**
     * Elimina un empleado por su ID.
     * 
     * @param id ID del empleado a eliminar
     * @return 204 si se elimina correctamente o 404 si no se encuentra
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer id) {
        if (empleadoService.findById(id).isPresent()) {
            empleadoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
