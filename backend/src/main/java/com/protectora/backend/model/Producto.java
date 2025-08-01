package com.protectora.backend.model;

// ==================== PRODUCTOS ====================
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoría")
    private Categoria categoria;

    private Integer cantidad;
    private String unidad;

    @Column(name = "stock_mínimo")
    private Integer stockMinimo;

    @OneToMany(mappedBy = "producto")
    private List<Compra> compras;

    public enum Categoria {
        comida, medicamento, limpieza, juguetes, otros
    }
}
