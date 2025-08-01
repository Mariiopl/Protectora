package com.protectora.backend.model;

// ==================== HORAS TRABAJADAS ====================
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "HorasTrabajadas")
public class HorasTrabajadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Integer idRegistro;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    private LocalDate fecha;
    private Double horas;
}
