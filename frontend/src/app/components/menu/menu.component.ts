import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { HasRoleDirective } from '../../directives/has-role.directive';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterLink, CommonModule, HasRoleDirective, RouterLinkActive],
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css'],
})
export class MenuComponent implements OnInit {
  username: string | null = null;
  tipoUsuario: string | null = null;

  constructor(public authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    // Al iniciar, cargamos los datos del usuario
    this.username = this.authService.getUsername();
    this.tipoUsuario = this.authService.getTipoUsuario();

    // Si el estado de login cambia (por ejemplo, tras iniciar o cerrar sesión)
    this.authService.isLoggedIn().subscribe(() => {
      this.username = this.authService.getUsername();
      this.tipoUsuario = this.authService.getTipoUsuario();
    });
  }

  logout() {
    this.authService.logout();
    this.username = null;
    this.tipoUsuario = null;
    this.router.navigate(['/login']);
  }
}
