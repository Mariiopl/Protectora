import { Component, OnInit } from '@angular/core';
import { UsuariosService, Usuario } from '../services/usuario.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-usuarios',
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css'],
  imports: [CommonModule],
  standalone: true,
})
export class UsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];

  constructor(private usuariosService: UsuariosService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.usuariosService.getUsuarios().subscribe({
      next: (data) => (this.usuarios = data),
      error: (err) => console.error('Error cargando usuarios', err),
    });
  }

  nuevoUsuario() {
    const nombre = prompt('Nombre del usuario:');
    const email = prompt('Email:');
    const contrasena = prompt('Contraseña:');
    const tipoUsuario = prompt(
      'Tipo de usuario (adoptante, voluntario, empleado, administrador):'
    ) as 'adoptante' | 'voluntario' | 'empleado' | 'administrador';

    if (nombre && email && contrasena && tipoUsuario) {
      const usuario: Usuario = { nombre, email, contrasena, tipoUsuario };
      this.usuariosService.crearUsuario(usuario).subscribe({
        next: () => this.cargarUsuarios(),
        error: (err) => console.error('Error creando usuario', err),
      });
    }
  }

  editarUsuario(usuario: Usuario) {
    const nombre = prompt('Nombre:', usuario.nombre) || usuario.nombre;
    const email = prompt('Email:', usuario.email) || usuario.email;
    const tipoUsuario =
      (prompt(
        'Tipo de usuario (adoptante, voluntario, empleado, administrador):',
        usuario.tipoUsuario
      ) as 'adoptante' | 'voluntario' | 'empleado' | 'administrador') ||
      usuario.tipoUsuario;

    const actualizado: Usuario = { ...usuario, nombre, email, tipoUsuario };
    this.usuariosService
      .actualizarUsuario(usuario.idUsuario!, actualizado)
      .subscribe({
        next: () => this.cargarUsuarios(),
        error: (err) => console.error('Error actualizando usuario', err),
      });
  }

  eliminarUsuario(id: number | undefined) {
    if (!id) return;
    if (confirm('¿Estás seguro de eliminar este usuario?')) {
      this.usuariosService.eliminarUsuario(id).subscribe({
        next: () => this.cargarUsuarios(),
        error: (err) => console.error('Error eliminando usuario', err),
      });
    }
  }
}
