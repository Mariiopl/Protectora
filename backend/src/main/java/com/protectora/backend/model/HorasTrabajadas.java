package com.protectora.backend.model;

// ==================== HORAS TRABAJADAS ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "HorasTrabajadas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
