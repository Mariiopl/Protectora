package com.protectora.backend.services;

import com.protectora.backend.model.HorasTrabajadas;
import com.protectora.backend.repository.HorasTrabajadasRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorasTrabajadasService {

    private final HorasTrabajadasRepository horasTrabajadasRepository;

    public HorasTrabajadasService(HorasTrabajadasRepository horasTrabajadasRepository) {
        this.horasTrabajadasRepository = horasTrabajadasRepository;
    }

    public List<HorasTrabajadas> findAll() {
        return horasTrabajadasRepository.findAll();
    }

    public Optional<HorasTrabajadas> findById(Integer id) {
        return horasTrabajadasRepository.findById(id);
    }

    public HorasTrabajadas save(HorasTrabajadas horasTrabajadas) {
        return horasTrabajadasRepository.save(horasTrabajadas);
    }

    public void deleteById(Integer id) {
        horasTrabajadasRepository.deleteById(id);
    }
}
