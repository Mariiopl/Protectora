import { Component, OnInit } from '@angular/core';
import { ProductoService, Producto } from '../services/producto.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css'],
  imports: [CommonModule],
  standalone: true,
})
export class ProductosComponent implements OnInit {
  productos: Producto[] = [];
  error: string | null = null;

  constructor(private productoService: ProductoService) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos() {
    this.productoService.getProductos().subscribe({
      next: (data) => {
        this.productos = data;
      },
      error: (err) => {
        console.error('Error cargando productos', err);
        this.error = 'No se pudieron cargar los productos.';
      },
    });
  }

  eliminarProducto(id: number) {
    if (!confirm('¿Seguro que quieres eliminar este producto?')) return;

    this.productoService.deleteProducto(id).subscribe({
      next: () => {
        this.productos = this.productos.filter((p) => p.idProducto !== id);
      },
      error: (err) => {
        console.error('Error eliminando producto', err);
        alert('No se pudo eliminar el producto.');
      },
    });
  }
}
