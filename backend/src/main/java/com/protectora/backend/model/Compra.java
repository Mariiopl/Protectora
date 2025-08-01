package com.protectora.backend.model;

// ==================== COMPRAS ====================
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Compras")
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
