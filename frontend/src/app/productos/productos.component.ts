// productos.component.ts
import { Component, OnInit } from '@angular/core';
import { Producto, ProductoService } from '../services/producto.service';
import { Compra, CompraService } from '../services/compra.service';
import { FormsModule, NgModel } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css'],
  imports: [FormsModule],
})
export class ProductosComponent implements OnInit {
  productos: Producto[] = [];
  nuevoProducto: Producto = {
    nombre: '',
    categoria: 'otros',
    cantidad: 0,
    unidad: '',
    stockMinimo: 0,
  };
  compra: Partial<Compra> = {};

  constructor(
    private productoService: ProductoService,
    private compraService: CompraService
  ) {}

  ngOnInit() {
    this.cargarProductos();
  }

  cargarProductos() {
    this.productoService.listar().subscribe((p) => (this.productos = p));
  }

  agregarProducto() {
    this.productoService.crear(this.nuevoProducto).subscribe(() => {
      this.nuevoProducto = {
        nombre: '',
        categoria: 'otros',
        cantidad: 0,
        unidad: '',
        stockMinimo: 0,
      };
      this.cargarProductos();
    });
  }

  comprar(p: Producto) {
    if (!this.compra.cantidad || !this.compra.proveedor) return;
    const c: Compra = { ...this.compra, producto: p } as Compra;
    this.compraService.comprar(c).subscribe(() => {
      this.compra = {};
      this.cargarProductos();
    });
  }
}
