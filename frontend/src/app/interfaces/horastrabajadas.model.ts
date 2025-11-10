import { Empleado } from './empleado.model';

export interface HorasTrabajadas {
  idRegistro: number;
  empleado: Empleado;
  fecha: string;
  horas: number;
}
