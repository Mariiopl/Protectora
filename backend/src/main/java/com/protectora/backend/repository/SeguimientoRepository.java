package com.protectora.backend.repository;

import com.protectora.backend.model.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Integer> {
    List<Seguimiento> findByAdopcionIdAdopcion(Integer idAdopcion);

    List<Seguimiento> findByEstadoAndFechaProgramadaBefore(Seguimiento.Estado estado, java.time.LocalDate fecha);
}
