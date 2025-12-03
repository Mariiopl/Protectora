import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdopcionService } from '../../services/adopcion.service';

@Component({
  selector: 'app-solicitudes-adopciones',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './solicitudes-adopciones.component.html',
  styleUrls: ['./solicitudes-adopciones.component.css'],
})
export class SolicitudesAdopcionesComponent implements OnInit {
  solicitudes: any[] = [];
  cargando = true;
  error = '';

  constructor(private adopcionService: AdopcionService) {}

  ngOnInit(): void {
    this.cargarSolicitudes();
  }

  cargarSolicitudes() {
    this.adopcionService.getSolicitudesPendientes().subscribe({
      next: (data) => {
        this.solicitudes = data;
        this.cargando = false;
      },
      error: () => {
        this.error = 'Error al cargar solicitudes';
        this.cargando = false;
      },
    });
  }

  aprobar(id: number) {
    this.adopcionService.cambiarEstado(id, 'aceptada').subscribe(() => {
      this.cargarSolicitudes();
    });
  }

  rechazar(id: number) {
    this.adopcionService.cambiarEstado(id, 'rechazada').subscribe(() => {
      this.cargarSolicitudes();
    });
  }
}
