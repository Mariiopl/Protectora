export interface Adopcion {
  idAdopcion: number;
  idUsuario: number;
  idMascota: number;
  estado:
    | 'pendiente'
    | 'en evaluación'
    | 'aceptada'
    | 'rechazada'
    | 'finalizada';
  fechaSolicitud?: string;
  fechaAdopcion?: string;
  experiencia?: string;
  tipoVivienda?: string;
  comentarios?: string;
}
