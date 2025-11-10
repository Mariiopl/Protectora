import { Usuario } from './usuario.model';

export interface Empleado {
  idEmpleado: number;
  rol:
    | 'cuidador'
    | 'limpieza'
    | 'veterinario'
    | 'administrador'
    | 'adopciones'
    | 'stock';
  salario?: number;
  horario?: string;
  fechaAlta?: string;
  usuario?: Usuario; // Relación con Usuario
}
