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
    @NotNull(message = "El usuario es obligatorio")
    private Usuario usuario;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad debe ser positiva")
    private Double cantidad;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "El tipo es obligatorio")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @NotNull(message = "El método de pago es obligatorio")
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
// {
// "usuario": {
// "idUsuario": 1
// },
// "cantidad": 50.0,
// "fecha": "2025-09-19",
// "tipo": "puntual",
// "metodoPago": "paypal"
// }
