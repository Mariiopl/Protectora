package com.protectora.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "El producto es obligatorio")
    private Producto producto;

    @NotNull(message = "El proveedor es obligatorio")
    @Size(max = 100, message = "El nombre del proveedor no puede superar los 100 caracteres")
    private String proveedor;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    @NotNull(message = "El costo total es obligatorio")
    @Min(value = 0, message = "El costo total no puede ser negativo")
    @Column(name = "costo_total")
    private Double costoTotal;
}
// {
// "producto": {
// "idProducto": 1
// },
// "proveedor": "Proveedor Central",
// "fecha": "2025-09-19",
// "cantidad": 10,
// "costoTotal": 250.50
// }
