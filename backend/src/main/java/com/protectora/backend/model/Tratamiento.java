package com.protectora.backend.model;

// ==================== TRATAMIENTOS ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Tratamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratamiento")
    private Integer idTratamiento;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Column(name = "descripción")
    private String descripcion;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "veterinario")
    private Empleado veterinario;

    public enum Tipo {
        vacuna, desparasitación, cirugía, otro
    }
}
