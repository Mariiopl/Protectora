package com.protectora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Define any custom query methods if needed

}
