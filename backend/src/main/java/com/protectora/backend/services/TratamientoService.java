package com.protectora.backend.services;

import com.protectora.backend.model.Tratamiento;
import com.protectora.backend.repository.TratamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TratamientoService {

    private final TratamientoRepository tratamientoRepository;

    public TratamientoService(TratamientoRepository tratamientoRepository) {
        this.tratamientoRepository = tratamientoRepository;
    }

    public List<Tratamiento> findAll() {
        return tratamientoRepository.findAll();
    }

    public Optional<Tratamiento> findById(Integer id) {
        return tratamientoRepository.findById(id);
    }

    public Tratamiento save(Tratamiento tratamiento) {
        return tratamientoRepository.save(tratamiento);
    }

    public void deleteById(Integer id) {
        tratamientoRepository.deleteById(id);
    }
}
