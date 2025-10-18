package com.protectora.backend.controller;

import com.protectora.backend.model.Compra;
import com.protectora.backend.services.CompraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @GetMapping
    public List<Compra> getAllCompras() {
        return compraService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompraById(@PathVariable Integer id) {
        return compraService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Compra> createCompra(@Valid @RequestBody Compra compra) {
        Compra saved = compraService.save(compra);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Compra> updateCompra(
            @PathVariable Integer id,
            @Valid @RequestBody Compra compraDetails) {

        return compraService.findById(id)
                .map(compra -> {
                    compra.setProducto(compraDetails.getProducto());
                    compra.setProveedor(compraDetails.getProveedor());
                    compra.setFecha(compraDetails.getFecha());
                    compra.setCantidad(compraDetails.getCantidad());
                    compra.setCostoTotal(compraDetails.getCostoTotal());
                    Compra updated = compraService.save(compra);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Integer id) {
        if (compraService.findById(id).isPresent()) {
            compraService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
