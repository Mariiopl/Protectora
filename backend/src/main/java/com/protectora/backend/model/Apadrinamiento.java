package com.protectora.backend.model;

// ==================== APADRINAMIENTOS ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Apadrinamientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Apadrinamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apadrinamiento")
    private Integer idApadrinamiento;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.activo;

    public enum Estado {
        activo, finalizado
    }
}
