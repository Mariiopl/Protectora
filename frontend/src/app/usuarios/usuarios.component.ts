import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuariosService, Usuario } from '../services/usuario.service';
import Swal from 'sweetalert2';
import { EmpleadoService } from '../services/empleado.service';

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
    rolEmpleado: null, // AÑADIR ESTO
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
      rolEmpleado: usuario.tipoUsuario === 'empleado' ? 'adopciones' : null, // valor por defecto
    };

    // Solo intentar cargar rol si el usuario ya tiene empleado (opcional)
    // if (usuario.tipoUsuario === 'empleado') {
    //   this.empleadoService.getById(usuario.idUsuario!).subscribe({
    //     next: (emp) => this.form.rolEmpleado = emp.rol,
    //     error: () => console.log('Empleado no encontrado, se usará rol por defecto')
    //   });
    // }

    this.modalVisible = true;
  }

  guardar() {
    const data = { ...this.form };

    // Solo enviar rolEmpleado si es tipo empleado
    if (data.tipoUsuario !== 'empleado') {
      delete data.rolEmpleado;
    }

    if (this.editando) {
      this.usuariosService.actualizarUsuario(data.idUsuario, data).subscribe({
        next: () => {
          Swal.fire({
            icon: 'success',
            title: 'Usuario actualizado',
            showConfirmButton: false,
            timer: 1500,
          });
          this.cargarUsuarios();
          this.cerrarModal();
        },
        error: (err) => {
          console.error('Error actualizando usuario', err);
          Swal.fire({
            icon: 'error',
            title: 'Error al actualizar',
            text: err.error?.message || 'Ocurrió un error inesperado',
          });
        },
      });
    } else {
      this.usuariosService.crearUsuario(data).subscribe({
        next: () => {
          /* ... similar ... */
        },
        error: (err) => {
          /* ... similar ... */
        },
      });
    }
  }

  eliminarUsuario(id: number | undefined) {
    console.log('ID recibido:', id);

    if (!id) return;

    Swal.fire({
      title: '¿Eliminar usuario?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.usuariosService.eliminarUsuario(id).subscribe({
          next: () => {
            Swal.fire({
              icon: 'success',
              title: 'Usuario eliminado',
              showConfirmButton: false,
              timer: 1500,
            });
            this.cargarUsuarios();
          },
          error: (err) => {
            console.error('Error eliminando usuario', err);

            if (err.status === 409) {
              Swal.fire({
                icon: 'error',
                title: 'No se puede eliminar',
                text: 'Este usuario tiene datos asociados y no puede eliminarse.',
              });
            } else {
              Swal.fire({
                icon: 'error',
                title: 'Error al eliminar',
                text: 'Ocurrió un error inesperado.',
              });
            }
          },
        });
      }
    });
  }

  cerrarModal() {
    this.modalVisible = false;
  }
}
