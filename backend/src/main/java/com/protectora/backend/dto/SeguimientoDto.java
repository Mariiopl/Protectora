package com.protectora.backend.dto;

import com.protectora.backend.model.Seguimiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeguimientoDto {

    private Integer idSeguimiento;
    private Integer idAdopcion;
    private LocalDate fechaProgramada;
    private LocalDateTime fechaRealizada;
    private String mensaje;
    private String urlImagen;
    private Seguimiento.Estado estado;

    public static SeguimientoDto fromEntity(Seguimiento s) {
        return new SeguimientoDto(
                s.getIdSeguimiento(),
                s.getAdopcion() != null ? s.getAdopcion().getIdAdopcion() : null,
                s.getFechaProgramada(),
                s.getFechaRealizada(),
                s.getMensaje(),
                s.getUrlImagen(),
                s.getEstado());
    }
}
