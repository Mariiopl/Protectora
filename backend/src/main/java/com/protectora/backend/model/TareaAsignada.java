package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TareasAsignadas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaAsignada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignación")
    private Integer idAsignacion;

    @ManyToOne
    @JoinColumn(name = "id_tarea")
    @NotNull(message = "La tarea es obligatoria")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    @NotNull(message = "El empleado es obligatorio")
    private Empleado empleado;

    @Size(max = 500, message = "Las observaciones no pueden superar los 500 caracteres")
    private String observaciones;
}
// {
// "tarea": {
// "idTarea": 1
// },
// "empleado": {
// "idEmpleado": 2
// },
// "observaciones": "El empleado completó la tarea correctamente."
// }
