import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../interfaces/producto.model';

@Component({
  selector: 'app-producto-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './producto-form.component.html',
  styleUrls: ['./producto-form.component.css'],
})
export class ProductoFormComponent {
  producto: Producto = {
    idProducto: 0,
    nombre: '',
    categoria: 'otros',
    cantidad: 0,
    unidad: '',
    stockMinimo: 0,
  };

  constructor(private productoService: ProductoService) {}

  guardarProducto() {
    this.productoService.createProducto(this.producto).subscribe(() => {
      alert('Producto creado');
      this.producto = {
        idProducto: 0,
        nombre: '',
        categoria: 'otros',
        cantidad: 0,
        unidad: '',
        stockMinimo: 0,
      };
    });
  }
}
