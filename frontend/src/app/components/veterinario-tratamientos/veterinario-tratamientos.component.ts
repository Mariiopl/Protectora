import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { VeterinarioService } from '../../services/veterinario.service';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import {
  Tratamiento,
  TratamientoDTO,
} from '../../interfaces/tratamiento.model';

@Component({
  standalone: true,
  selector: 'app-veterinario-tratamientos',
  templateUrl: './veterinario-tratamientos.component.html',
  styleUrls: ['./veterinario-tratamientos.component.css'],
  imports: [CommonModule, ReactiveFormsModule],
})
export class VeterinarioTratamientosComponent implements OnInit {
  tratamientos: Tratamiento[] = [];
  form: FormGroup;
  mensajeError = '';

  constructor(
    private veterinarioService: VeterinarioService,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      idMascota: ['', Validators.required],
      tipo: ['', Validators.required],
      descripcion: ['', Validators.required],
      fecha: ['', Validators.required],
      idVeterinario: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.cargarTratamientos();
  }

  cargarTratamientos(): void {
    this.veterinarioService.getTratamientos().subscribe({
      next: (data) => {
        this.tratamientos = data; // tipos ya son compatibles
      },
      error: () => console.error('Error cargando tratamientos'),
    });
  }

  crearTratamiento(): void {
    if (this.form.invalid) {
      this.mensajeError = 'Todos los campos son obligatorios';
      return;
    }

    const dto: TratamientoDTO = this.form.value;

    this.veterinarioService.createTratamiento(dto).subscribe({
      next: (nuevo) => {
        this.tratamientos.push(nuevo);
        this.form.reset();
        this.mensajeError = '';
      },
      error: () => {
        this.mensajeError = 'Error al crear el tratamiento';
      },
    });
  }
}
