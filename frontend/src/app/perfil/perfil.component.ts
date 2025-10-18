import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Perfil, PerfilService } from '../services/perfil.service';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css'],
  imports: [CommonModule, ReactiveFormsModule],
  standalone: true,
})
export class PerfilComponent implements OnInit {
  perfilForm!: FormGroup;
  perfil!: Perfil;
  mensaje: string = '';
  error: string = '';

  constructor(private fb: FormBuilder, private perfilService: PerfilService) {}

  ngOnInit(): void {
    const userId = 1; // ID del usuario actual

    this.perfilService.getPerfil(userId).subscribe({
      next: (data) => {
        this.perfil = data;
        this.initForm();
      },
      error: (err) => {
        console.error('Error cargando perfil', err);
        this.error = 'No se pudo cargar el perfil';
      },
    });
  }

  initForm() {
    this.perfilForm = this.fb.group({
      nombre: [
        this.perfil.nombre,
        [Validators.required, Validators.maxLength(100)],
      ],
      email: [
        this.perfil.email,
        [Validators.required, Validators.email, Validators.maxLength(100)],
      ],
      contrasena: [
        this.perfil.contrasena,
        [Validators.required, Validators.minLength(6)],
      ],
      telefono: [this.perfil.telefono, [Validators.maxLength(20)]],
      direccion: [this.perfil.direccion],
    });
  }

  guardarCambios() {
    if (this.perfilForm.invalid) return;

    const updatedPerfil: Perfil = {
      ...this.perfil,
      ...this.perfilForm.value,
    };

    this.perfilService
      .updatePerfil(this.perfil.idUsuario!, updatedPerfil)
      .subscribe({
        next: () => {
          this.mensaje = 'Datos actualizados correctamente';
          this.error = '';
        },
        error: (err) => {
          console.error('Error actualizando perfil', err);
          this.error = 'No se pudieron actualizar los datos';
          this.mensaje = '';
        },
      });
  }
}
