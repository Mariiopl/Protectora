package com.protectora.backend.model;

// ==================== TAREAS ASIGNADAS ====================
import jakarta.persistence.*;

@Entity
@Table(name = "TareasAsignadas")
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
