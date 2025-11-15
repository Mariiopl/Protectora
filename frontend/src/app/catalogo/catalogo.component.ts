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
  mascotaSeleccionada: any = null;
  fotoSeleccionada: File | null = null;

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

  nuevaMascota() {
    this.mascotaSeleccionada = {}; // ✅ objeto vacío
    this.mostrarModal = true;
  }

  cerrarModal() {
    this.mostrarModal = false;
    this.mascotaSeleccionada = null;
    this.fotoSeleccionada = null;
  }

  guardarMascota() {
    if (!this.mascotaSeleccionada) return;

    const formData = new FormData();
    formData.append(
      'mascota',
      new Blob([JSON.stringify(this.mascotaSeleccionada)], {
        type: 'application/json',
      })
    );

    if (this.fotoSeleccionada) {
      formData.append('foto', this.fotoSeleccionada);
    }

    if (!this.mascotaSeleccionada.idMascota) {
      this.mascotaService.crearMascotaConFoto(formData).subscribe({
        next: () => {
          this.cerrarModal();
          this.cargarMascotas();
        },
        error: (err) => console.error(err),
      });
    } else {
      this.mascotaService
        .actualizarMascotaConFoto(this.mascotaSeleccionada.idMascota, formData)
        .subscribe({
          next: () => {
            this.cerrarModal();
            this.cargarMascotas();
          },
          error: (err) => console.error(err),
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
  /* === SUBIR FOTO === */
  subirFoto(event: any) {
    this.fotoSeleccionada = event.target.files[0];
  }
}
