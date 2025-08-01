package com.protectora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    // Define any custom query methods if needed

}
