package com.protectora.backend.services;

import com.protectora.backend.model.TareaAsignada;
import com.protectora.backend.repository.TareaAsignadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para manejar la lógica de negocio de las tareas asignadas.
 * Gestiona la creación, consulta, actualización y eliminación de TareaAsignada.
 */
@Service
public class TareaAsignadaService {

    private final TareaAsignadaRepository tareaAsignadaRepository;

    public TareaAsignadaService(TareaAsignadaRepository tareaAsignadaRepository) {
        this.tareaAsignadaRepository = tareaAsignadaRepository;
    }

    // =================================================
    // CONSULTAS
    // =================================================

    /**
     * Obtiene todas las tareas asignadas.
     * 
     * @return Lista de todas las tareas asignadas.
     */
    public List<TareaAsignada> findAll() {
        return tareaAsignadaRepository.findAll();
    }

    /**
     * Obtiene una tarea asignada por su ID.
     * 
     * @param id ID de la tarea asignada
     * @return Optional con la tarea si existe, o vacío si no se encuentra.
     */
    public Optional<TareaAsignada> findById(Integer id) {
        return tareaAsignadaRepository.findById(id);
    }

    // =================================================
    // CREACIÓN / ACTUALIZACIÓN
    // =================================================

    /**
     * Guarda una nueva tarea asignada o actualiza una existente.
     * 
     * @param tareaAsignada Tarea a guardar
     * @return La tarea guardada en la base de datos
     */
    public TareaAsignada save(TareaAsignada tareaAsignada) {
        return tareaAsignadaRepository.save(tareaAsignada);
    }

    // =================================================
    // ELIMINACIÓN
    // =================================================

    /**
     * Elimina una tarea asignada por su ID.
     * 
     * @param id ID de la tarea a eliminar
     */
    public void deleteById(Integer id) {
        tareaAsignadaRepository.deleteById(id);
    }
}
