package com.protectora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.HorasTrabajadas;

@Repository
public interface HorasTrabajadasRepository extends JpaRepository<HorasTrabajadas, Integer> {
    // Define any custom query methods if needed

}
