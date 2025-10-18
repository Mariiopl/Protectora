import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './auth.guard';
import { CatalogoComponent } from './catalogo/catalogo.component';
import { AdopcionesComponent } from './adopciones/adopciones.component';
import { ComprasComponent } from './compras/compras.component';
import { EmpleadosComponent } from './empleados/empleados.component';
import { PerfilComponent } from './perfil/perfil.component';
import { ProductosComponent } from './productos/productos.component';
import { SeguimientosComponent } from './seguimientos/seguimientos.component';
import { TareasComponent } from './tareas/tareas.component';
import { TratamientoComponent } from './tratamiento/tratamiento.component';
import { TurnosComponent } from './turnos/turnos.component';
import { UsuariosComponent } from './usuarios/usuarios.component';

export const appRoutes: Routes = [
  { path: '', redirectTo: '/registro', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'catalogo', component: CatalogoComponent, canActivate: [AuthGuard] },
  {
    path: 'adopciones',
    component: AdopcionesComponent,
    canActivate: [AuthGuard],
  },
  { path: 'compras', component: ComprasComponent, canActivate: [AuthGuard] },
  {
    path: 'empleados',
    component: EmpleadosComponent,
    canActivate: [AuthGuard],
  },
  { path: 'perfil', component: PerfilComponent, canActivate: [AuthGuard] },
  {
    path: 'productos',
    component: ProductosComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'seguimientos',
    component: SeguimientosComponent,
    canActivate: [AuthGuard],
  },
  { path: 'tareas', component: TareasComponent, canActivate: [AuthGuard] },
  { path: 'turnos', component: TurnosComponent, canActivate: [AuthGuard] },
  { path: 'usuarios', component: UsuariosComponent, canActivate: [AuthGuard] },
  {
    path: 'tratamiento',
    component: TratamientoComponent,
    canActivate: [AuthGuard],
  },
];
