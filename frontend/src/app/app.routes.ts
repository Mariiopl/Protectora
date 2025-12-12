import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './guards/auth.guard';
import { CatalogoComponent } from './catalogo/catalogo.component';
import { AdopcionesComponent } from './adopciones/adopciones.component';
import { PerfilComponent } from './perfil/perfil.component';
import { UsuariosComponent } from './usuarios/usuarios.component';
// import { CompraListComponent } from './inventario/compra-list/compra-list.component';
// import { CompraFormComponent } from './inventario/compra-form/compra-form.component';
import { MascotasAdoptablesComponent } from './mascotas-adoptables/mascotas-adoptables.component';
import { VeterinarioTratamientosComponent } from './components/veterinario-tratamientos/veterinario-tratamientos.component';
import { SolicitudesAdopcionesComponent } from './components/solicitudes-adopciones/solicitudes-adopciones.component';
import { AniadirSeguimientosComponent } from './aniadir-seguimientos/aniadir-seguimientos.component';

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
    path: 'mascotas/en-adopcion',
    component: MascotasAdoptablesComponent,
    canActivate: [AuthGuard],
  },
  //Veterinario
  {
    path: 'veterinario-tratamientos',
    component: VeterinarioTratamientosComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'solicitudesAdopcion',
    component: SolicitudesAdopcionesComponent,
    canActivate: [AuthGuard],
  },
  { path: 'perfil', component: PerfilComponent, canActivate: [AuthGuard] },

  { path: 'usuarios', component: UsuariosComponent, canActivate: [AuthGuard] },

  {
    path: 'aniadir-seguimientos',
    component: AniadirSeguimientosComponent,
    canActivate: [AuthGuard],
  },

  // // RUTAS DE INVENTARIO
  // {
  //   path: 'inventario/compras',
  //   component: CompraListComponent,
  //   canActivate: [AuthGuard],
  // },
  // {
  //   path: 'inventario/compras/nuevo',
  //   component: CompraFormComponent,
  //   canActivate: [AuthGuard],
  // },
];
