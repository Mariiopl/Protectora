import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CompraService } from '../../services/compra.service';
import { ProductoService } from '../../services/producto.service';
import { Compra } from '../../interfaces/compra.model';
import { Producto } from '../../interfaces/producto.model';

@Component({
  selector: 'app-compra-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './compra-form.component.html',
  styleUrls: ['./compra-form.component.css'],
})
export class CompraFormComponent {
  compra: Compra = {
    idCompra: 0,
    idProducto: 0,
    proveedor: '',
    fecha: '',
    cantidad: 0,
    costoTotal: 0,
  };
  productos: Producto[] = [];

  constructor(
    private compraService: CompraService,
    private productoService: ProductoService
  ) {
    this.cargarProductos();
  }

  cargarProductos() {
    this.productoService.getProductos().subscribe({
      next: (data) => (this.productos = data),
      error: (err) => console.error(err),
    });
  }

  guardarCompra() {
    this.compraService.createCompra(this.compra).subscribe(() => {
      alert('Compra registrada');
      this.compra = {
        idCompra: 0,
        idProducto: 0,
        proveedor: '',
        fecha: '',
        cantidad: 0,
        costoTotal: 0,
      };
    });
  }
}
