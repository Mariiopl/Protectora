export interface Mascota {
  idMascota: number;
  nombre: string;
  especie: string;
  raza: string;
  edad: number;
  tamano: 'Pequeño' | 'Mediano' | 'Grande';
  sexo: 'macho' | 'hembra';
  caracter: string;
  necesidadesEspeciales?: string;
  esterilizado?: boolean;
  vacunado?: boolean;
  desparasitado?: boolean;
  estadoAdopcion?: 'adoptable' | 'en_proceso' | 'adoptado';
  historia?: string;
  ubicacion?: string;
  fechaIngreso?: string;
  fotos: FotoMascota[];
  mostrarDetalles?: boolean;
}
export interface FotoMascota {
  idFoto: number;
  urlImagen: string;
}
