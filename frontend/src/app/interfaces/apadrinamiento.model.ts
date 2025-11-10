export interface Apadrinamiento {
  idApadrinamiento: number;
  idUsuario: number;
  idMascota: number;
  fechaInicio: string; // ISO date
  fechaFin?: string; // ISO date
  estado: 'activo' | 'finalizado';
}
