export interface Mascota {
  idMascota: number;
  nombre: string;
  especie: string;
  raza: string;
  edad: number;
  tamano: 'pequeño' | 'mediano' | 'grande';
  sexo: 'macho' | 'hembra';
  caracter: string;
  necesidadesEspeciales?: string;
  esterilizado?: boolean;
  vacunado?: boolean;
  desparasitado?: boolean;
  estadoAdopcion?: 'adoptable' | 'en proceso' | 'adoptado';
  historia?: string;
  ubicacion?: string;
  fechaIngreso?: string;
  foto?: string;
  mostrarDetalles?: boolean;
}
