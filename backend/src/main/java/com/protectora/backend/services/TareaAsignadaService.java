package com.protectora.backend.services;

import com.protectora.backend.model.TareaAsignada;
import com.protectora.backend.repository.TareaAsignadaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaAsignadaService {

    private final TareaAsignadaRepository tareaAsignadaRepository;

    public TareaAsignadaService(TareaAsignadaRepository tareaAsignadaRepository) {
        this.tareaAsignadaRepository = tareaAsignadaRepository;
    }

    public List<TareaAsignada> findAll() {
        return tareaAsignadaRepository.findAll();
    }

    public Optional<TareaAsignada> findById(Integer id) {
        return tareaAsignadaRepository.findById(id);
    }

    public TareaAsignada save(TareaAsignada tareaAsignada) {
        return tareaAsignadaRepository.save(tareaAsignada);
    }

    public void deleteById(Integer id) {
        tareaAsignadaRepository.deleteById(id);
    }
}
