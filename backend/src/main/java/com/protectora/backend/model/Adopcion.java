package com.protectora.backend.model;

// ==================== ADOPCIONES ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Adopciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adopcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adopcion")
    private Integer idAdopcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.pendiente;

    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    @Column(name = "fecha_adopcion")
    private LocalDate fechaAdopcion;

    private String experiencia;
    @Column(name = "tipo_vivienda")
    private String tipoVivienda;
    private String comentarios;

    @OneToMany(mappedBy = "adopcion")
    private List<Seguimiento> seguimientos;

    public enum Estado {
        pendiente, en_evaluación, aceptada, rechazada, finalizada
    }
}
