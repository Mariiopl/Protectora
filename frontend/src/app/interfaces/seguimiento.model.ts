export interface Seguimiento {
  idSeguimiento?: number;
  idAdopcion: number;
  fecha: string; // ISO date
  mensaje?: string | null;
  urlImagen?: string | null;
  urlVideo?: string | null;
  tipo: 'texto' | 'foto' | 'video' | 'videollamada';
  enlaceVideollamada?: string | null;
}
