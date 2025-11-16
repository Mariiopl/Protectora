import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { HasRoleDirective } from '../../directives/has-role.directive';
import Swal from 'sweetalert2';

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
  menuOpen = false;
  dropdowns = { gestion: false };

  constructor(
    public authService: AuthService,
    private router: Router,
    private eRef: ElementRef
  ) {}

  ngOnInit(): void {
    // Al iniciar, cargamos los datos del usuario
    this.username = this.authService.getUsername();
    this.tipoUsuario = this.authService.getTipoUsuario();

    // Detectar navegación para cerrar dropdown automáticamente
    this.router.events.subscribe(() => {
      this.closeDropdowns();
      this.menuOpen = false;
    });

    // Si el estado de login cambia (por ejemplo, tras iniciar o cerrar sesión)
    this.authService.isLoggedIn().subscribe(() => {
      this.username = this.authService.getUsername();
      this.tipoUsuario = this.authService.getTipoUsuario();
    });
  }

  logout() {
    Swal.fire({
      title: '¿Cerrar sesión?',
      text: 'Se cerrará tu sesión actual',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, salir',
      cancelButtonText: 'Cancelar',
    }).then((result) => {
      if (result.isConfirmed) {
        this.authService.logout();
        this.username = null;
        this.tipoUsuario = null;
        this.router.navigate(['/login']);

        Swal.fire(
          'Sesión cerrada',
          'Has cerrado sesión correctamente.',
          'success'
        );
      }
    });
  }

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  toggleDropdown(menu: 'gestion') {
    this.dropdowns[menu] = !this.dropdowns[menu];
  }

  closeDropdowns() {
    this.dropdowns = { gestion: false };
  }

  /** 🔹 Detectar clic fuera del menú para cerrar el dropdown */
  @HostListener('document:click', ['$event'])
  handleClickOutside(event: Event) {
    if (!this.eRef.nativeElement.contains(event.target)) {
      this.closeDropdowns();
    }
  }
}
