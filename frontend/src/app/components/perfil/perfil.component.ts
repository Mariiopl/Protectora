import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css'],
})
export class PerfilComponent implements OnInit {
  nombre: string | null = '';
  email: string | null = '';
  tipoUsuario: string | null = '';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    // Se pueden cargar más datos del usuario desde el backend si quieres
    this.nombre = localStorage.getItem('nombre') || 'Usuario';
    this.email = localStorage.getItem('email') || '';
    this.tipoUsuario = this.authService.getTipoUsuario() || '';
  }

  guardarPerfil() {
    alert('Perfil actualizado (simulado)');
  }
}
