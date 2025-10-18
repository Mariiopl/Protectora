import { Component, OnInit } from '@angular/core';
import {
  Tratamiento,
  TratamientoService,
} from '../services/tratamiento.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tratamientos',
  templateUrl: './tratamiento.component.html',
  styleUrls: ['./tratamiento.component.css'],
  imports: [CommonModule],
  standalone: true,
})
export class TratamientoComponent implements OnInit {
  tratamientos: Tratamiento[] = [];
  error: string | null = null;

  constructor(private tratamientoService: TratamientoService) {}

  ngOnInit(): void {
    this.cargarTratamientos();
  }

  cargarTratamientos() {
    this.tratamientoService.getTratamientos().subscribe({
      next: (data) => (this.tratamientos = data),
      error: (err) => {
        console.error('Error cargando tratamientos', err);
        this.error = 'No se pudieron cargar los tratamientos.';
      },
    });
  }

  eliminarTratamiento(id: number) {
    if (!confirm('¿Seguro que quieres eliminar este tratamiento?')) return;

    this.tratamientoService.deleteTratamiento(id).subscribe({
      next: () => {
        this.tratamientos = this.tratamientos.filter(
          (t) => t.idTratamiento !== id
        );
      },
      error: (err) => {
        console.error('Error eliminando tratamiento', err);
        alert('No se pudo eliminar el tratamiento.');
      },
    });
  }
}
