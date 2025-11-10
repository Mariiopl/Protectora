import { Empleado } from './empleado.model';
import { Mascota } from './mascota.model';

export interface Tratamiento {
  idTratamiento: number;
  mascota: Mascota;
  tipo: 'vacuna' | 'desparasitación' | 'cirugía' | 'otro';
  descripcion?: string;
  fecha: string;
  veterinario: Empleado;
}
