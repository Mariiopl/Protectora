import { Component, OnInit } from '@angular/core';
import { AdopcionService } from '../services/adopcion.service';
import { Adopcion } from '../interfaces/adopcion.model';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-adopciones',
  standalone: true,
  templateUrl: './adopciones.component.html',
  styleUrls: ['./adopciones.component.css'],
  imports: [CommonModule, FormsModule, NgIf, NgFor],
})
export class AdopcionesComponent implements OnInit {
  adopciones: Adopcion[] = [];
  cargando = true;
  seleccionada: Adopcion | null = null;

  constructor(private adopcionService: AdopcionService) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');

    if (!token) {
      console.error('No hay token disponible');
      this.cargando = false;
      return;
    }

    this.adopcionService.getMisAdopciones(token).subscribe({
      next: (res) => {
        this.adopciones = res.map((a: any) => ({
          ...a,
          fotoMascota: a.fotoMascota
            ? 'http://localhost:8080/api/mascotas/imagenes/' + a.fotoMascota
            : 'assets/no-image.jpg',
        }));

        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.cargando = false;
      },
    });
  }

  verAdopcion(adopcion: Adopcion) {
    this.seleccionada = adopcion;
  }

  cerrarModal() {
    this.seleccionada = null;
  }
}
