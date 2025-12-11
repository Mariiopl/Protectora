package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Tratamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratamiento")
    private Integer idTratamiento;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    @NotNull(message = "La mascota es obligatoria")
    @JsonIgnoreProperties({ "tratamientos" }) // evita recursión al serializar
    private Mascota mascota;

    @NotNull(message = "El tipo de tratamiento es obligatorio")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    @Column(name = "descripcion")
    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "veterinario")
    @NotNull(message = "El veterinario es obligatorio")
    @JsonIgnoreProperties({ "tratamientos" }) // evita recursión al serializar
    private Empleado veterinario;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.pendiente; // nuevo campo con valor por defecto

    public enum Tipo {
        vacuna, desparasitación, cirugía, otro
    }

    public enum Estado {
        pendiente, informado, realizado
    }
}
