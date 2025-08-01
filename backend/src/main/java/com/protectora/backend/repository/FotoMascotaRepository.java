package com.protectora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.FotoMascota;

@Repository
public interface FotoMascotaRepository extends JpaRepository<FotoMascota, Integer> {
    // Define any custom query methods if needed

}
