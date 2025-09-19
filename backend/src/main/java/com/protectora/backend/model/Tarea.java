package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @NotNull(message = "El tipo de tarea es obligatorio")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    @Column(name = "descripción")
    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.pendiente;

    @OneToMany(mappedBy = "tarea")
    @JsonIgnore
    private List<TareaAsignada> tareasAsignadas;

    public enum Tipo {
        limpieza, mantenimiento, alimentación, otro
    }

    public enum Estado {
        pendiente, en_curso, completada
    }
}
// {
// "tipo": "limpieza",
// "descripcion": "Limpiar las jaulas de los gatos",
// "fecha": "2025-09-20",
// "estado": "pendiente"
// }
