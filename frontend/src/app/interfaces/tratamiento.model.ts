export interface TratamientoDTO {
  idMascota: number;
  tipo: 'vacuna' | 'desparasitación' | 'cirugía' | 'otro';
  descripcion: string;
  fecha: string;
  idVeterinario: number;
  estado?: 'pendiente' | 'informado' | 'realizado'; // opcional, puede venir por defecto
}

export interface Tratamiento {
  idTratamiento?: number; // opcional, porque al crear puede venir undefined
  mascota: {
    idMascota: number;
    nombre: string;
    foto?: string; // si quieres mostrar la foto
  };
  tipo: string;
  descripcion: string;
  fecha: string;
  estado: 'pendiente' | 'informado' | 'realizado'; // obligatorio, ya que el backend lo devuelve
  veterinario?: {
    idEmpleado: number;
    nombre: string;
  };
}
