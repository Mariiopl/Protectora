package com.protectora.backend.services;

import com.protectora.backend.model.Donacion;
import com.protectora.backend.repository.DonacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonacionService {

    private final DonacionRepository donacionRepository;

    public DonacionService(DonacionRepository donacionRepository) {
        this.donacionRepository = donacionRepository;
    }

    public List<Donacion> findAll() {
        return donacionRepository.findAll();
    }

    public Optional<Donacion> findById(Integer id) {
        return donacionRepository.findById(id);
    }

    public Donacion save(Donacion donacion) {
        return donacionRepository.save(donacion);
    }

    public void deleteById(Integer id) {
        donacionRepository.deleteById(id);
    }
}
