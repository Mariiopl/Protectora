package com.protectora.backend.dto;

import com.protectora.backend.model.Adopcion;
import com.protectora.backend.model.Mascota;

import java.time.LocalDate;

public record AdopcionDto(
        Integer idAdopcion,
        Adopcion.Estado estado,
        LocalDate fechaSolicitud,
        LocalDate fechaAdopcion,
        String experiencia,
        String tipoVivienda,
        String comentarios,
        Integer idUsuario,
        String nombreUsuario,
        Integer idMascota,
        String nombreMascota,
        String fotoMascota) {

    // Conversión de Entity a DTO
    public static AdopcionDto fromEntity(Adopcion adopcion) {
        Mascota mascota = adopcion.getMascota();
        return new AdopcionDto(
                adopcion.getIdAdopcion(),
                adopcion.getEstado(),
                adopcion.getFechaSolicitud(),
                adopcion.getFechaAdopcion(),
                adopcion.getExperiencia(),
                adopcion.getTipoVivienda(),
                adopcion.getComentarios(),
                adopcion.getUsuario() != null ? adopcion.getUsuario().getIdUsuario() : null,
                adopcion.getUsuario() != null ? adopcion.getUsuario().getNombre() : null,
                mascota != null ? mascota.getIdMascota() : null,
                mascota != null ? mascota.getNombre() : null,
                mascota != null ? mascota.getFoto() : null);
    }
}
