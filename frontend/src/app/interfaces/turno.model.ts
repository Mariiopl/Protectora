import { Empleado } from './empleado.model';

export interface Turno {
  idTurno: number;
  empleado: Empleado;
  fecha: string;
  horaInicio?: string;
  horaFin?: string;
  estado?: 'asignado' | 'intercambio_solicitado' | 'aprobado' | 'cancelado';
}
