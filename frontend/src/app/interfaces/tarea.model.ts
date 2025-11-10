export interface Tarea {
  idTarea: number;
  tipo: 'limpieza' | 'mantenimiento' | 'alimentación' | 'otro';
  descripcion?: string;
  fecha: string; // ISO date
  estado: 'pendiente' | 'en curso' | 'completada';
}
