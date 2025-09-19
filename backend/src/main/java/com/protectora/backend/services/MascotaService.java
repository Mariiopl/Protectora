package com.protectora.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.protectora.backend.model.Mascota;
import com.protectora.backend.repository.MascotaRepository;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    // Obtener todas las mascotas
    public List<Mascota> findAll() {
        return mascotaRepository.findAll();
    }

    // Buscar mascota por ID
    public Optional<Mascota> findById(Integer id) {
        return mascotaRepository.findById(id);
    }

    // Guardar o actualizar mascota
    public Mascota save(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    // Eliminar mascota por ID
    public void deleteById(Integer id) {
        mascotaRepository.deleteById(id);
    }

    // Buscar mascotas por nombre
    public Optional<Mascota> findByNombre(String nombre) {
        return mascotaRepository.findByNombre(nombre);
    }
}
