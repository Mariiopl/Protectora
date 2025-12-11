// aniadir-seguimientos.component.ts
import { Component, OnInit } from '@angular/core';
import { AdopcionService } from '../services/adopcion.service';
import {
  SeguimientoService,
  Seguimiento,
} from '../services/seguimiento.service';
import { Adopcion } from '../interfaces/adopcion.model';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-administracion-adopciones',
  standalone: true,
  templateUrl: './aniadir-seguimientos.component.html',
  styleUrls: ['./aniadir-seguimientos.component.css'],
  imports: [CommonModule, FormsModule, NgIf, NgFor],
})
export class AniadirSeguimientosComponent implements OnInit {
  adopciones: Adopcion[] = [];
  cargando = true;
  seleccionada: Adopcion | null = null;

  mensajeSeguimiento: string = '';

  constructor(
    private adopcionService: AdopcionService,
    private seguimientoService: SeguimientoService
  ) {}

  ngOnInit(): void {
    this.cargarAdopciones();
  }

  cargarAdopciones() {
    this.cargando = true;
    this.adopcionService.getAceptadas().subscribe({
      next: (res) => {
        this.adopciones = res.map((a) => ({
          ...a,
          fotoMascota: a.fotoMascota
            ? 'http://localhost:8080/api/mascotas/imagenes/' + a.fotoMascota
            : 'assets/no-image.jpg',
        }));
        this.cargando = false;
      },
      error: () => (this.cargando = false),
    });
  }

  verAdopcion(adopcion: Adopcion) {
    this.seleccionada = adopcion;
    this.mensajeSeguimiento = '';
  }

  cerrarModal() {
    this.seleccionada = null;
    this.mensajeSeguimiento = '';
  }

  agregarSeguimiento() {
    if (!this.seleccionada || !this.mensajeSeguimiento) return;

    const seguimiento: Seguimiento = {
      idAdopcion: this.seleccionada.idAdopcion,
      tipo: 'foto', // siempre foto
      fechaProgramada: new Date().toISOString().split('T')[0], // hoy
      mensaje: this.mensajeSeguimiento,
      estado: 'pendiente',
    };

    this.seguimientoService.crearSeguimiento(seguimiento).subscribe({
      next: () => this.cerrarModal(),
      error: (err) => console.error(err),
    });
  }
}
