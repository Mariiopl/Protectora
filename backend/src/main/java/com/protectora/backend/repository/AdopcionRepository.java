package com.protectora.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.Adopcion;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Integer> {
    List<Adopcion> findByUsuario_IdUsuario(Integer idUsuario);
}
