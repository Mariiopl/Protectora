package com.protectora.backend.model;

// ==================== TAREAS ASIGNADAS ====================
import jakarta.persistence.*;
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
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    private String observaciones;
}
