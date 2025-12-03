package com.protectora.backend.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudAdopcionDto {
    private int idAdopcion;
    private String estado;
    private Date fechaSolicitud;
    private int idUsuario;
    private String nombreUsuario;
    private int idMascota;
    private String nombreMascota;
}