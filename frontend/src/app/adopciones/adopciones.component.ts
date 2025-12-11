import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdopcionService } from '../services/adopcion.service';
import {
  SeguimientoService,
  Seguimiento,
} from '../services/seguimiento.service';
import { VeterinarioService } from '../services/veterinario.service';
import { Adopcion } from '../interfaces/adopcion.model';
import { Tratamiento } from '../interfaces/tratamiento.model';

@Component({
  selector: 'app-mis-adopciones',
  standalone: true,
  templateUrl: './adopciones.component.html',
  styleUrls: ['./adopciones.component.css'],
  imports: [CommonModule, FormsModule, NgIf, NgFor],
})
export class AdopcionesComponent implements OnInit {
  adopciones: Adopcion[] = [];
  cargando = true;

  seleccionada: Adopcion | null = null;
  seguimientos: Seguimiento[] = [];
  tratamientos: Tratamiento[] = [];

  // Arrays filtrados para la vista
  tratamientosPendientes: Tratamiento[] = [];
  tratamientosInformados: Tratamiento[] = [];

  constructor(
    private adopcionService: AdopcionService,
    private seguimientoService: SeguimientoService,
    private veterinarioService: VeterinarioService
  ) {}

  ngOnInit(): void {
    const token = localStorage.getItem('token');
    if (!token) return;

    this.adopcionService.getMisAdopciones(token).subscribe({
      next: (res) => {
        this.adopciones = res.map((a) => ({
          ...a,
          fotoMascota: a.fotoMascota
            ? `http://localhost:8080/api/mascotas/imagenes/${a.fotoMascota}`
            : 'assets/no-image.jpg',
        }));
        this.cargando = false;
      },
      error: () => (this.cargando = false),
    });
  }

  verAdopcion(adopcion: Adopcion) {
    this.seleccionada = adopcion;
    this.cargarSeguimientos(adopcion.idAdopcion);
    this.cargarTratamientos(adopcion.idMascota);
  }

  cerrarModal() {
    this.seleccionada = null;
    this.seguimientos = [];
    this.tratamientos = [];
    this.tratamientosPendientes = [];
    this.tratamientosInformados = [];
  }

  cargarSeguimientos(idAdopcion: number) {
    this.seguimientoService.getPorAdopcion(idAdopcion).subscribe({
      next: (res) => (this.seguimientos = res),
      error: (err) => console.error(err),
    });
  }

  cargarTratamientos(idMascota: number) {
    this.veterinarioService.getTratamientosPorMascota(idMascota).subscribe({
      next: (res) => {
        this.tratamientos = res;
        this.tratamientosPendientes = res.filter(
          (t) => t.estado === 'pendiente'
        );
        this.tratamientosInformados = res.filter(
          (t) => t.estado === 'informado'
        );
      },
      error: (err) => console.error(err),
    });
  }

  marcarCompletado(s: Seguimiento) {
    if (!s.idSeguimiento) return;

    this.seguimientoService.marcarCompletado(s.idSeguimiento).subscribe({
      next: () => (s.estado = 'completado'),
      error: (err) => console.error(err),
    });
  }

  onFileSelect(event: Event, seguimiento: Seguimiento) {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    const file = input.files[0];
    const formData = new FormData();
    formData.append('file', file);

    if (!seguimiento.idSeguimiento) return;

    this.seguimientoService
      .subirArchivo(seguimiento.idSeguimiento, formData)
      .subscribe({
        next: () => console.log('Foto subida correctamente'),
        error: (err) => console.error(err),
      });
  }

  marcarTratamientoInformado(t: Tratamiento) {
    if (!t.idTratamiento) return;
    this.veterinarioService.marcarInformado(t.idTratamiento).subscribe({
      next: (res) => {
        t.estado = res.estado;
        // Actualizar arrays filtrados
        this.tratamientosPendientes = this.tratamientos.filter(
          (t) => t.estado === 'pendiente'
        );
        this.tratamientosInformados = this.tratamientos.filter(
          (t) => t.estado === 'informado'
        );
      },
      error: (err) => console.error(err),
    });
  }
}
