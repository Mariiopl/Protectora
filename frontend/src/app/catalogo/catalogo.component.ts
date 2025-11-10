import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MascotaService } from '../services/mascota.service';
import { Mascota } from '../interfaces/mascota.model';

@Component({
  selector: 'app-catalogo',
  standalone: true,
  templateUrl: './catalogo.component.html',
  styleUrls: ['./catalogo.component.css'],
  imports: [CommonModule, FormsModule],
})
export class CatalogoComponent implements OnInit {
  mascotas: Mascota[] = [];
  cargando = true;
  mostrarModal = false;
  mascotaSeleccionada: Mascota | null = null;

  constructor(private mascotaService: MascotaService) {}

  ngOnInit(): void {
    this.cargarMascotas();
  }

  cargarMascotas() {
    this.cargando = true;
    this.mascotaService.getAdoptables().subscribe({
      next: (data) => {
        this.mascotas = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error cargando mascotas', err);
        this.cargando = false;
      },
    });
  }

  abrirModal(mascota: Mascota) {
    this.mascotaSeleccionada = { ...mascota };
    this.mostrarModal = true;
  }

  cerrarModal() {
    this.mostrarModal = false;
    this.mascotaSeleccionada = null;
  }

  guardarCambios() {
    if (this.mascotaSeleccionada) {
      this.mascotaService
        .actualizarMascota(this.mascotaSeleccionada)
        .subscribe({
          next: () => {
            this.cerrarModal();
            this.cargarMascotas();
          },
          error: (err) => console.error('Error actualizando mascota', err),
        });
    }
  }
  toggleDetalles(mascota: any) {
    mascota.mostrarDetalles = !mascota.mostrarDetalles;
  }
}
