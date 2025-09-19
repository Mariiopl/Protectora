package com.protectora.backend.services;

import com.protectora.backend.model.FotoMascota;
import com.protectora.backend.repository.FotoMascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FotoMascotaService {

    private final FotoMascotaRepository fotoMascotaRepository;

    public FotoMascotaService(FotoMascotaRepository fotoMascotaRepository) {
        this.fotoMascotaRepository = fotoMascotaRepository;
    }

    public List<FotoMascota> findAll() {
        return fotoMascotaRepository.findAll();
    }

    public Optional<FotoMascota> findById(Integer id) {
        return fotoMascotaRepository.findById(id);
    }

    public FotoMascota save(FotoMascota fotoMascota) {
        return fotoMascotaRepository.save(fotoMascota);
    }

    public void deleteById(Integer id) {
        fotoMascotaRepository.deleteById(id);
    }
}
