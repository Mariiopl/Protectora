package com.protectora.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.protectora.backend.model.Tarea;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
    // Define any custom query methods if needed

}
