package com.protectora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.Donacion;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Integer> {
    // Define any custom query methods if needed

}
