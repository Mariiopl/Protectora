package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Apadrinamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Apadrinamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apadrinamiento")
    private Integer idApadrinamiento;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    @NotNull(message = "La mascota es obligatoria")
    private Mascota mascota;

    @Column(name = "fecha_inicio")
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado es obligatorio")
    private Estado estado = Estado.activo;

    public enum Estado {
        activo, finalizado
    }
}
// {
// "usuario": {
// "idUsuario": 1
// },
// "mascota": {
// "idMascota": 3
// },
// "fechaInicio": "2025-09-19",
// "fechaFin": null,
// "estado": "activo"
// }
