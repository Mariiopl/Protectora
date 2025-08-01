package com.protectora.backend.model;

// ==================== TURNOS ====================
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Integer idTurno;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    private LocalDate fecha;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fin")
    private LocalTime horaFin;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.asignado;

    public enum Estado {
        asignado, intercambio_solicitado, aprobado, cancelado
    }
}
