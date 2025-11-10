import { inject, Injectable } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // Roles permitidos para esta ruta
  const allowedRoles: string[] = route.data['roles'];

  const userRole = authService.getTipoUsuario(); // Devuelve el rol del usuario logueado

  if (userRole && allowedRoles.includes(userRole)) {
    return true;
  }

  // Redirigir a login o página de error
  router.navigate(['/login']);
  return false;
};
