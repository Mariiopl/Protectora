package com.protectora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.Mascota;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Integer> {
    // Define any custom query methods if needed

}
