import { Component, OnInit } from '@angular/core';
import { MascotaService } from '../services/mascota.service';
import { AdopcionService } from '../services/adopcion.service';
import { Mascota } from '../interfaces/mascota.model';
import { Adopcion } from '../interfaces/adopcion.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mascotas-adoptables',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mascotas-adoptables.component.html',
  styleUrls: ['./mascotas-adoptables.component.css'],
})
export class MascotasAdoptablesComponent implements OnInit {
  mascotas: Mascota[] = [];
  cargando = false;

  mostrarModal = false;
  mascotaSeleccionada: Mascota | null = null;

  // FORMULARIO DE ADOPCIÓN BASADO EN LA INTERFAZ
  adopcion: Partial<Adopcion> = {
    experiencia: '',
    tipoVivienda: '',
    comentarios: '',
  };

  constructor(
    private mascotaService: MascotaService,
    private adopcionService: AdopcionService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.cargarAdoptables();
  }

  cargarAdoptables() {
    this.cargando = true;
    this.mascotaService.getAdoptable().subscribe({
      next: (data) => {
        this.mascotas = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar mascotas adoptables', err);
        this.cargando = false;
      },
    });
  }

  toggleDetalles(mascota: Mascota) {
    mascota.mostrarDetalles = !mascota.mostrarDetalles;
  }

  adoptar(mascota: Mascota) {
    this.mascotaSeleccionada = mascota;
    this.mostrarModal = true;
  }

  cerrarModal() {
    this.mostrarModal = false;
    this.mascotaSeleccionada = null;

    this.adopcion = {
      experiencia: '',
      tipoVivienda: '',
      comentarios: '',
    };
  }

  enviarSolicitud() {
    if (!this.mascotaSeleccionada) return;

    const solicitud: Adopcion = {
      idAdopcion: 0,
      idUsuario: this.authService.getUserId()!, // ahora sí tendrá valor
      idMascota: this.mascotaSeleccionada.idMascota!,
      fechaSolicitud: new Date().toISOString(),
      experiencia: this.adopcion.experiencia!,
      tipoVivienda: this.adopcion.tipoVivienda!,
      comentarios: this.adopcion.comentarios!,
    };

    this.adopcionService.crearSolicitudAdopcion(solicitud).subscribe({
      next: (res) => {
        this.cerrarModal();

        // Recargar mascotas adoptables
        this.mascotaService.getAdoptable().subscribe({
          next: (listaActualizada) => {
            this.mascotas = listaActualizada;
          },
        });
        // SweetAlert para éxito
        Swal.fire({
          icon: 'success',
          title: '¡Solicitud enviada!',
          text: 'Tu solicitud de adopción ha sido enviada correctamente.',
          confirmButtonColor: '#1cb6c1',
        });
        this.cerrarModal(); // si estás usando modal
      },
      error: (err) => {
        // SweetAlert para error
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'No se pudo enviar la solicitud. Intenta de nuevo.',
          confirmButtonColor: '#e74c3c',
        });
        console.error('Error enviando solicitud', err);
      },
    });
  }
}
