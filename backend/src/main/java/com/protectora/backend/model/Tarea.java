package com.protectora.backend.model;

// ==================== TAREAS ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Tareas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private Integer idTarea;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "descripción")
    private String descripcion;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.pendiente;

    @OneToMany(mappedBy = "tarea")
    private List<TareaAsignada> tareasAsignadas;

    public enum Tipo {
        limpieza, mantenimiento, alimentación, otro
    }

    public enum Estado {
        pendiente, en_curso, completada
    }
}
