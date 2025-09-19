package com.protectora.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    Optional<Mascota> findByNombre(String nombre);// Define any custom query methods if needed

}
