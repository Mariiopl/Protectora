package com.protectora.backend.dto;

import com.protectora.backend.model.Tratamiento;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class TratamientoDto {

    @NotNull
    private Integer idMascota;

    @NotNull
    private Tratamiento.Tipo tipo;

    private String descripcion;

    @NotNull
    private LocalDate fecha;

    @NotNull
    private Integer idVeterinario;

    private Tratamiento.Estado estado; // opcional, si no se envía será 'pendiente' por defecto

    // Constructor vacío
    public TratamientoDto() {
    }

    // Constructor con todos los campos
    public TratamientoDto(Integer idMascota, Tratamiento.Tipo tipo, String descripcion,
            LocalDate fecha, Integer idVeterinario, Tratamiento.Estado estado) {
        this.idMascota = idMascota;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.idVeterinario = idVeterinario;
        this.estado = estado;
    }

    // Getters y setters
    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
    }

    public Tratamiento.Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tratamiento.Tipo tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(Integer idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    public Tratamiento.Estado getEstado() {
        return estado;
    }

    public void setEstado(Tratamiento.Estado estado) {
        this.estado = estado;
    }
}
