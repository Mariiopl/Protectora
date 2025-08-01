package com.protectora.backend.model;

// ==================== DONACIONES ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Donaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donacion")
    private Integer idDonacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private Double cantidad;
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "método_pago")
    private MetodoPago metodoPago;

    public enum Tipo {
        puntual, recurrente
    }

    public enum MetodoPago {
        paypal, stripe, tarjeta
    }
}
