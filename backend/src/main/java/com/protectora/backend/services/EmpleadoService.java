package com.protectora.backend.services;

import com.protectora.backend.model.Empleado;
import com.protectora.backend.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejar la lógica de negocio relacionada con los empleados.
 * Encapsula operaciones como obtener, crear, actualizar y eliminar empleados,
 * así como consultas específicas como obtener solo veterinarios.
 */
@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    // =================================================
    // CONSULTAS
    // =================================================

    /**
     * Obtiene todos los empleados registrados en el sistema.
     * 
     * @return Lista de empleados
     */
    public List<Empleado> findAll() {
        return empleadoRepository.findAll();
    }

    /**
     * Obtiene todos los empleados cuyo rol es "veterinario".
     * Útil para filtrar solo los empleados que trabajan como veterinarios.
     * 
     * @return Lista de empleados veterinarios
     */
    public List<Empleado> findVeterinarios() {
        return empleadoRepository.findByRol("veterinario");
    }

    /**
     * Busca un empleado por su ID.
     * 
     * @param id ID del empleado
     * @return Optional con el empleado si existe, vacío si no
     */
    public Optional<Empleado> findById(Integer id) {
        return empleadoRepository.findById(id);
    }

    // =================================================
    // CREACIÓN / ACTUALIZACIÓN
    // =================================================

    /**
     * Guarda un empleado en la base de datos.
     * Puede ser utilizado tanto para crear uno nuevo como para actualizar uno
     * existente.
     * 
     * @param empleado Empleado a guardar
     * @return Empleado guardado con ID y demás campos actualizados
     */
    public Empleado save(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    // =================================================
    // ELIMINACIÓN
    // =================================================

    /**
     * Elimina un empleado por su ID.
     * 
     * @param id ID del empleado a eliminar
     */
    public void deleteById(Integer id) {
        empleadoRepository.deleteById(id);
    }
}
