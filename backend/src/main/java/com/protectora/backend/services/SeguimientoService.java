package com.protectora.backend.services;

import com.protectora.backend.model.Seguimiento;
import com.protectora.backend.repository.SeguimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;

    public SeguimientoService(SeguimientoRepository seguimientoRepository) {
        this.seguimientoRepository = seguimientoRepository;
    }

    public List<Seguimiento> findAll() {
        return seguimientoRepository.findAll();
    }

    public Optional<Seguimiento> findById(Integer id) {
        return seguimientoRepository.findById(id);
    }

    public Seguimiento save(Seguimiento seguimiento) {
        return seguimientoRepository.save(seguimiento);
    }

    public void deleteById(Integer id) {
        seguimientoRepository.deleteById(id);
    }
}
