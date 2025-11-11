import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './guards/auth.guard';
import { CatalogoComponent } from './catalogo/catalogo.component';
import { AdopcionesComponent } from './adopciones/adopciones.component';
import { EmpleadosComponent } from './empleados/empleados.component';
import { PerfilComponent } from './perfil/perfil.component';
import { TareasComponent } from './tareas/tareas.component';
import { TratamientoComponent } from './tratamiento/tratamiento.component';
import { TurnosComponent } from './turnos/turnos.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { CompraListComponent } from './inventario/compra-list/compra-list.component';
import { CompraFormComponent } from './inventario/compra-form/compra-form.component';

export const appRoutes: Routes = [
  { path: '', redirectTo: '/register', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'catalogo', component: CatalogoComponent, canActivate: [AuthGuard] },
  {
    path: 'adopciones',
    component: AdopcionesComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'empleados',
    component: EmpleadosComponent,
    canActivate: [AuthGuard],
  },
  { path: 'perfil', component: PerfilComponent, canActivate: [AuthGuard] },
  { path: 'tareas', component: TareasComponent, canActivate: [AuthGuard] },
  { path: 'turnos', component: TurnosComponent, canActivate: [AuthGuard] },
  { path: 'usuarios', component: UsuariosComponent, canActivate: [AuthGuard] },
  {
    path: 'tratamiento',
    component: TratamientoComponent,
    canActivate: [AuthGuard],
  },

  // RUTAS DE INVENTARIO
  {
    path: 'inventario/compras',
    component: CompraListComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'inventario/compras/nuevo',
    component: CompraFormComponent,
    canActivate: [AuthGuard],
  },
];
