import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { VeterinarioService } from '../../services/veterinario.service';
import { MascotaService } from '../../services/mascota.service';
import {
  Tratamiento,
  TratamientoDTO,
} from '../../interfaces/tratamiento.model';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';

interface Mascota {
  idMascota: number;
  nombre: string;
  foto?: string;
}

@Component({
  standalone: true,
  selector: 'app-veterinario-tratamientos',
  templateUrl: './veterinario-tratamientos.component.html',
  styleUrls: ['./veterinario-tratamientos.component.css'],
  imports: [CommonModule, ReactiveFormsModule],
})
export class VeterinarioTratamientosComponent implements OnInit {
  tratamientos: Tratamiento[] = [];
  mascotasAdoptadas: Mascota[] = [];
  form: FormGroup;
  mensajeError = '';
  mascotaSeleccionadaId: number | null = null;

  constructor(
    private veterinarioService: VeterinarioService,
    private mascotaService: MascotaService,
    private auth: AuthService,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      idMascota: ['', Validators.required],
      tipo: ['', Validators.required],
      descripcion: ['', Validators.required],
      fecha: ['', Validators.required],
      idVeterinario: [this.auth.getUserId(), Validators.required],
      estado: ['pendiente', Validators.required],
    });
  }

  ngOnInit(): void {
    this.cargarTratamientos();
    this.cargarMascotasAdoptadas();
  }

  cargarTratamientos(): void {
    this.veterinarioService.getTratamientos().subscribe({
      next: (data) => {
        this.tratamientos = data;
      },
      error: () => console.error('Error cargando tratamientos'),
    });
  }

  cargarMascotasAdoptadas(): void {
    this.mascotaService.getAdoptadas().subscribe({
      next: (data) => (this.mascotasAdoptadas = data),
      error: () => console.error('Error cargando mascotas adoptadas'),
    });
  }

  seleccionarMascota(id: number): void {
    this.mascotaSeleccionadaId = id;
    this.form.controls['idMascota'].setValue(id);
  }

  crearTratamiento(): void {
    if (this.form.invalid) {
      Swal.fire({
        icon: 'warning',
        title: 'Campos incompletos',
        text: 'Todos los campos son obligatorios.',
      });
      return;
    }

    const dto: TratamientoDTO = this.form.value;

    this.veterinarioService.createTratamiento(dto).subscribe({
      next: (nuevo) => {
        this.tratamientos.push(nuevo);
        this.form.reset({ estado: 'pendiente' });
        this.mensajeError = '';
        this.mascotaSeleccionadaId = null;

        Swal.fire({
          icon: 'success',
          title: 'Tratamiento creado',
          text: 'El tratamiento se ha registrado correctamente.',
          timer: 1500,
          showConfirmButton: false,
        });
      },
      error: () => {
        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: 'Hubo un problema al crear el tratamiento.',
        });
      },
    });
  }
  marcarTratamientoRealizado(t: Tratamiento): void {
    if (!t.idTratamiento) return;

    this.veterinarioService.marcarRealizado(t.idTratamiento).subscribe({
      next: (res) => {
        t.estado = res.estado; // Actualiza el estado en la tabla
      },
      error: (err) => {
        console.error('Error marcando tratamiento como realizado', err);
      },
    });
  }
}
