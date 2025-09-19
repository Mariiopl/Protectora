package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "El empleado es obligatorio")
    private Empleado empleado;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "Las horas trabajadas son obligatorias")
    @Min(value = 0, message = "Las horas no pueden ser negativas")
    private Double horas;
}
// {
// "empleado": {
// "idEmpleado": 1
// },
// "fecha": "2025-09-19",
// "horas": 8.5
// }
