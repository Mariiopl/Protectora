import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MascotaService } from '../services/mascota.service';
import { Mascota } from '../interfaces/mascota.model';

@Component({
  selector: 'app-catalogo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './catalogo.component.html',
  styleUrls: ['./catalogo.component.css'],
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

  eliminarMascota(mascota: Mascota) {
    if (confirm(`¿Seguro que quieres eliminar a ${mascota.nombre}?`)) {
      this.mascotaService.eliminarMascota(mascota.idMascota).subscribe({
        next: () => this.cargarMascotas(),
        error: (err) => console.error('Error eliminando mascota', err),
      });
    }
  }

  toggleDetalles(mascota: Mascota) {
    mascota.mostrarDetalles = !mascota.mostrarDetalles;
  }
}
