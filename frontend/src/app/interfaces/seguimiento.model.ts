export interface Seguimiento {
  idSeguimiento?: number;
  idAdopcion: number;
  fecha: string; // ISO date
  mensaje?: string;
  urlImagen?: string;
  urlVideo?: string;
  tipo: 'texto' | 'foto' | 'video' | 'videollamada';
  enlaceVideollamada?: string;
}
