import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MascotaService } from '../services/mascota.service';
import { Mascota } from '../interfaces/mascota.model';
import { HasRoleDirective } from '../directives/has-role.directive';
import Swal from 'sweetalert2';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-catalogo',
  standalone: true,
  imports: [CommonModule, FormsModule, HasRoleDirective],
  templateUrl: './catalogo.component.html',
  styleUrls: ['./catalogo.component.css'],
})
export class CatalogoComponent implements OnInit {
  mascotas: Mascota[] = [];
  cargando = true;
  mostrarModal = false;
  mascotaSeleccionada: any = null;
  fotoSeleccionada: File | null = null;

  constructor(
    private mascotaService: MascotaService,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.cargarMascotas();
  }

  cargarMascotas() {
    this.cargando = true;
    this.mascotaService.getAdoptadas().subscribe({
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
    formData.forEach((v, k) => console.log(k, v));
    // ----------------------------------

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
    Swal.fire({
      title: `¿Eliminar a ${mascota.nombre}?`,
      text: '¡No podrás revertir esto!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.mascotaService.eliminarMascota(mascota.idMascota).subscribe({
          next: () => {
            this.cargarMascotas();
            Swal.fire(
              '¡Eliminado!',
              `${mascota.nombre} ha sido eliminado.`,
              'success'
            );
          },
          error: (err) => {
            console.error('Error eliminando mascota', err);
            Swal.fire('Error', 'No se pudo eliminar la mascota.', 'error');
          },
        });
      }
    });
  }

  toggleDetalles(mascota: Mascota) {
    mascota.mostrarDetalles = !mascota.mostrarDetalles;
  }
  /* === SUBIR FOTO === */
  subirFoto(event: any) {
    this.fotoSeleccionada = event.target.files[0];
  }
  soloTexto(event: KeyboardEvent) {
    const char = event.key;
    // Si el carácter es un número, evitar que se escriba
    if (/\d/.test(char)) {
      event.preventDefault();
    }
  }
}
