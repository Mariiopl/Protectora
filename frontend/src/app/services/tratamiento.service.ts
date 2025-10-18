import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Tratamiento {
  idTratamiento?: number;
  mascota: { idMascota: number };
  tipo: 'vacuna' | 'desparasitación' | 'cirugía' | 'otro';
  descripcion: string;
  fecha: string; // YYYY-MM-DD
  veterinario: { idEmpleado: number };
}

@Injectable({
  providedIn: 'root',
})
export class TratamientoService {
  private apiUrl = 'http://localhost:8080/api/tratamientos';

  constructor(private http: HttpClient) {}

  getTratamientos(): Observable<Tratamiento[]> {
    return this.http.get<Tratamiento[]>(this.apiUrl);
  }

  createTratamiento(tratamiento: Tratamiento): Observable<Tratamiento> {
    return this.http.post<Tratamiento>(this.apiUrl, tratamiento);
  }

  updateTratamiento(
    id: number,
    tratamiento: Tratamiento
  ): Observable<Tratamiento> {
    return this.http.put<Tratamiento>(`${this.apiUrl}/${id}`, tratamiento);
  }

  deleteTratamiento(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
