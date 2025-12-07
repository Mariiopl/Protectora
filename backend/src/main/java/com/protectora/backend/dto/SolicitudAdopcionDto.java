package com.protectora.backend.dto;

import com.protectora.backend.model.Adopcion;
import java.time.LocalDate;

public record SolicitudAdopcionDto(
        Integer idAdopcion,
        Adopcion.Estado estado,
        LocalDate fechaSolicitud,
        Integer idUsuario,
        String nombreUsuario,
        Integer idMascota,
        String nombreMascota,
        String fotoMascota) {
}
