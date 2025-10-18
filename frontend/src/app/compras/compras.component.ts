import { Component, OnInit } from '@angular/core';
import { Compra, CompraService } from '../services/compras.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-compras',
  templateUrl: './compras.component.html',
  styleUrls: ['./compras.component.css'],
  imports: [CommonModule],
  standalone: true,
})
export class ComprasComponent implements OnInit {
  compras: Compra[] = [];
  error: string | null = null;

  constructor(private compraService: CompraService) {}

  ngOnInit(): void {
    this.cargarCompras();
  }

  cargarCompras() {
    this.compraService.getCompras().subscribe({
      next: (data) => (this.compras = data),
      error: (err) => {
        console.error('Error cargando compras', err);
        this.error = 'No se pudieron cargar las compras.';
      },
    });
  }

  eliminarCompra(id: number) {
    if (!confirm('¿Seguro que quieres eliminar esta compra?')) return;

    this.compraService.deleteCompra(id).subscribe({
      next: () => {
        this.compras = this.compras.filter((c) => c.idCompra !== id);
      },
      error: (err) => {
        console.error('Error eliminando compra', err);
        alert('No se pudo eliminar la compra.');
      },
    });
  }
}
