export interface Adopcion {
  idAdopcion: number;
  idUsuario: number;
  idMascota: number;
  fechaSolicitud?: string;
  experiencia?: string;
  tipoVivienda?: string;
  comentarios?: string;
}
