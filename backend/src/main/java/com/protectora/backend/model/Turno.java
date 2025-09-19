package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Turnos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Integer idTurno;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    @NotNull(message = "El empleado es obligatorio")
    private Empleado empleado;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.asignado;

    public enum Estado {
        asignado, intercambio_solicitado, aprobado, cancelado
    }
}
// {
// "empleado": {
// "idEmpleado": 2
// },
// "fecha": "2025-09-20",
// "horaInicio": "09:00:00",
// "horaFin": "17:00:00",
// "estado": "asignado"
// }
