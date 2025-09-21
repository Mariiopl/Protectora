import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  nombre = '';
  email = '';
  contrasena = '';
  telefono = '';
  direccion = '';
  tipoUsuario = 'adoptante';

  constructor(private auth: AuthService, private router: Router) {}

  onRegister() {
    this.auth
      .register({
        nombre: this.nombre,
        email: this.email,
        contrasena: this.contrasena,
        telefono: this.telefono,
        direccion: this.direccion,
        tipoUsuario: this.tipoUsuario,
      })
      .subscribe(() => {
        this.router.navigate(['/login']);
      });
  }
}
