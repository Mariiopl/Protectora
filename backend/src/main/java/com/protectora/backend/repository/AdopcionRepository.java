package com.protectora.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.protectora.backend.dto.SolicitudAdopcionDto;
import com.protectora.backend.model.Adopcion;
import com.protectora.backend.model.Adopcion.Estado;

@Repository
public interface AdopcionRepository extends JpaRepository<Adopcion, Integer> {

    List<Adopcion> findByUsuario_IdUsuario(Integer idUsuario);

    List<Adopcion> findByEstado(Estado estado);

    // @Query("""
    // SELECT new com.protectora.backend.dto.SolicitudAdopcionDto(
    // a.idAdopcion,
    // a.estado,
    // a.fechaSolicitud,
    // u.idUsuario,
    // u.nombre,
    // m.idMascota,
    // m.nombre
    // )
    // FROM Adopcion a
    // JOIN a.usuario u
    // JOIN a.mascota m
    // WHERE a.estado = com.protectora.backend.model.Adopcion.Estado.PENDIENTE
    // """)
    @Query("SELECT a FROM Adopcion a WHERE a.estado = 'PENDIENTE'")
    List<SolicitudAdopcionDto> obtenerPendientes();
}
