package com.protectora.backend.repository;

import com.protectora.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNombre(String nombre);

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);

}
