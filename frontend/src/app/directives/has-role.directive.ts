import {
  Directive,
  Input,
  TemplateRef,
  ViewContainerRef,
  OnInit,
  OnDestroy,
} from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Subscription } from 'rxjs';

@Directive({
  selector: '[appHasRole]',
  standalone: true,
})
export class HasRoleDirective implements OnInit, OnDestroy {
  private subscription: Subscription | null = null;
  private rolesPermitidos: string[] = [];

  constructor(
    private templateRef: TemplateRef<any>,
    private viewContainer: ViewContainerRef,
    private authService: AuthService
  ) {}

  ngOnInit() {
    // Suscribirse al estado de autenticación
    this.subscription = this.authService.isLoggedIn().subscribe(() => {
      this.updateView();
    });
  }

  ngOnDestroy() {
    // Limpiar la suscripción para evitar memory leaks
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  @Input() set appHasRole(roles: string | string[]) {
    this.rolesPermitidos = Array.isArray(roles) ? roles : [roles];
    this.updateView();
  }

  private updateView() {
    this.viewContainer.clear();
    const usuarioRol = this.authService.getTipoUsuario();

    const tienePermiso = this.rolesPermitidos.includes(usuarioRol || '');
    if (tienePermiso) {
      this.viewContainer.createEmbeddedView(this.templateRef);
    }
  }
}
