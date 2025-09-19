package com.protectora.backend.services;

import com.protectora.backend.model.Adopcion;
import com.protectora.backend.repository.AdopcionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdopcionService {

    private final AdopcionRepository adopcionRepository;

    public AdopcionService(AdopcionRepository adopcionRepository) {
        this.adopcionRepository = adopcionRepository;
    }

    public List<Adopcion> findAll() {
        return adopcionRepository.findAll();
    }

    public Optional<Adopcion> findById(Integer id) {
        return adopcionRepository.findById(id);
    }

    public Adopcion save(Adopcion adopcion) {
        return adopcionRepository.save(adopcion);
    }

    public void deleteById(Integer id) {
        adopcionRepository.deleteById(id);
    }
}
