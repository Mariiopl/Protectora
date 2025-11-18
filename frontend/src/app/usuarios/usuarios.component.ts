import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuariosService, Usuario } from '../services/usuario.service';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './usuarios.component.html',
  styleUrls: ['./usuarios.component.css'],
})
export class UsuariosComponent implements OnInit {
  usuarios: Usuario[] = [];

  modalVisible = false;
  editando = false;

  form: any = {
    idUsuario: null,
    nombre: '',
    email: '',
    telefono: '',
    direccion: '',
    tipoUsuario: 'adoptante',
    contrasena: '',
  };

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
    this.editando = false;
    this.form = {
      nombre: '',
      email: '',
      telefono: '',
      direccion: '',
      tipoUsuario: 'adoptante',
      contrasena: '',
    };
    this.modalVisible = true;
  }

  editarUsuario(usuario: Usuario) {
    this.editando = true;
    this.form = {
      idUsuario: usuario.idUsuario,
      nombre: usuario.nombre,
      email: usuario.email,
      telefono: usuario.telefono,
      direccion: usuario.direccion,
      tipoUsuario: usuario.tipoUsuario,
      contrasena: '',
    };
    this.modalVisible = true;
  }

  guardar() {
    const data = { ...this.form };

    // Si la contraseña está vacía → NO sobrescribir
    if (!data.contrasena) {
      delete data.contrasena;
    }

    if (this.editando) {
      this.usuariosService.actualizarUsuario(data.idUsuario, data).subscribe({
        next: () => {
          this.cargarUsuarios();
          this.cerrarModal();
        },
        error: (err) => console.error('Error actualizando usuario', err),
      });
    } else {
      this.usuariosService.crearUsuario(data).subscribe({
        next: () => {
          this.cargarUsuarios();
          this.cerrarModal();
        },
        error: (err) => console.error('Error creando usuario', err),
      });
    }
  }

  eliminarUsuario(id: number | undefined) {
    if (!id) return;
    if (confirm('¿Seguro que quieres eliminar este usuario?')) {
      this.usuariosService.eliminarUsuario(id).subscribe({
        next: () => this.cargarUsuarios(),
        error: (err) => console.error('Error eliminando usuario', err),
      });
    }
  }

  cerrarModal() {
    this.modalVisible = false;
  }
}
