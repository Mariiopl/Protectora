package com.protectora.backend.model;

// ==================== COMPRAS ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Compras")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Integer idCompra;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    private String proveedor;
    private LocalDate fecha;
    private Integer cantidad;

    @Column(name = "costo_total")
    private Double costoTotal;
}
