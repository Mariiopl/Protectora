export interface TratamientoDTO {
  idMascota: number;
  tipo: 'vacuna' | 'desparasitación' | 'cirugía' | 'otro';
  descripcion: string;
  fecha: string;
  idVeterinario: number;
}

export interface Tratamiento {
  idTratamiento?: number; // opcional, porque al crear puede venir undefined
  mascota: {
    idMascota: number;
    nombre: string;
    // agrega lo que necesites
  };
  tipo: string;
  descripcion: string;
  fecha: string;
  veterinario?: {
    idEmpleado: number;
    nombre: string;
  };
}
