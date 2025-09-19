package com.protectora.backend.services;

import com.protectora.backend.model.Turno;
import com.protectora.backend.repository.TurnoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public List<Turno> findAll() {
        return turnoRepository.findAll();
    }

    public Optional<Turno> findById(Integer id) {
        return turnoRepository.findById(id);
    }

    public Turno save(Turno turno) {
        return turnoRepository.save(turno);
    }

    public void deleteById(Integer id) {
        turnoRepository.deleteById(id);
    }
}
