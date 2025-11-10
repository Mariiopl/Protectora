import { Usuario } from './usuario.model';

export interface Donacion {
  idDonacion: number;
  usuario: Usuario;
  cantidad: number;
  fecha?: string;
  tipo?: 'puntual' | 'recurrente';
  metodoPago?: 'paypal' | 'stripe' | 'tarjeta';
}
