package com.protectora.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    Optional<Mascota> findByNombre(String nombre);

    List<Mascota> findByEstadoAdopcion(Mascota.EstadoAdopcion estadoAdopcion);

}
