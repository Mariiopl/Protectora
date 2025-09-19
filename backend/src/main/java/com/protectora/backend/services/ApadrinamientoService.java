package com.protectora.backend.services;

import com.protectora.backend.model.Apadrinamiento;
import com.protectora.backend.repository.ApadrinamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApadrinamientoService {

    private final ApadrinamientoRepository apadrinamientoRepository;

    public ApadrinamientoService(ApadrinamientoRepository apadrinamientoRepository) {
        this.apadrinamientoRepository = apadrinamientoRepository;
    }

    public List<Apadrinamiento> findAll() {
        return apadrinamientoRepository.findAll();
    }

    public Optional<Apadrinamiento> findById(Integer id) {
        return apadrinamientoRepository.findById(id);
    }

    public Apadrinamiento save(Apadrinamiento apadrinamiento) {
        return apadrinamientoRepository.save(apadrinamiento);
    }

    public void deleteById(Integer id) {
        apadrinamientoRepository.deleteById(id);
    }
}
