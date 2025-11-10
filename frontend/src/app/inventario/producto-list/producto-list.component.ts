import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductoService } from '../../services/producto.service';
import { FormsModule } from '@angular/forms';
import { Producto } from '../../interfaces/producto.model';

@Component({
  selector: 'app-producto-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './producto-list.component.html',
  styleUrls: ['./producto-list.component.css'],
})
export class ProductoListComponent implements OnInit {
  productos: Producto[] = [];
  cargando = true;

  constructor(private productoService: ProductoService) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos() {
    this.cargando = true;
    this.productoService.getProductos().subscribe({
      next: (data) => {
        this.productos = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.cargando = false;
      },
    });
  }

  eliminarProducto(id: number) {
    this.productoService
      .deleteProducto(id)
      .subscribe(() => this.cargarProductos());
  }
}
