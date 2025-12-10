import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Seguimiento {
  idSeguimiento?: number;
  idAdopcion: number;
  fechaProgramada: string;
  fechaRealizada?: string;
  mensaje?: string;
  urlImagen?: string; // foto subida
  tipo: 'foto'; // solo fotos
  estado?: 'pendiente' | 'completado';
}

@Injectable({
  providedIn: 'root',
})
export class SeguimientoService {
  private baseUrl = 'http://localhost:8080/api/seguimientos';

  constructor(private http: HttpClient) {}

  getPorAdopcion(idAdopcion: number): Observable<Seguimiento[]> {
    return this.http.get<Seguimiento[]>(
      `${this.baseUrl}/adopcion/${idAdopcion}`
    );
  }

  crearSeguimiento(seguimiento: Seguimiento): Observable<Seguimiento> {
    return this.http.post<Seguimiento>(this.baseUrl, seguimiento);
  }

  marcarCompletado(idSeguimiento: number): Observable<void> {
    return this.http.patch<void>(`${this.baseUrl}/${idSeguimiento}`, {
      estado: 'completado',
    });
  }
  subirArchivo(id: number, formData: FormData): Observable<any> {
    return this.http.post(`${this.baseUrl}/${id}/foto`, formData);
  }
}
