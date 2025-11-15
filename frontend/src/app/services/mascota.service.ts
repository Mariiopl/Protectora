import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mascota } from '../interfaces/mascota.model';

@Injectable({
  providedIn: 'root',
})
export class MascotaService {
  private apiUrl = 'http://localhost:8080/api/mascotas';

  constructor(private http: HttpClient) {}

  /** 🔹 Obtener todas las mascotas adoptables */
  getAdoptables(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(this.apiUrl);
  }

  /** 🔹 Obtener una mascota por ID */
  getMascotaById(id: number): Observable<Mascota> {
    return this.http.get<Mascota>(`${this.apiUrl}/${id}`);
  }

  /** 🔹 Actualizar una mascota existente */
  actualizarMascota(mascota: Mascota): Observable<Mascota> {
    return this.http.put<Mascota>(
      `${this.apiUrl}/${mascota.idMascota}`,
      mascota
    );
  }

  /** 🔹 Eliminar una mascota por ID */
  eliminarMascota(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  /** 🔹 Crear una nueva mascota (opcional con foto) */
  crearMascotaConFoto(formData: FormData): Observable<Mascota> {
    return this.http.post<Mascota>(this.apiUrl, formData);
  }

  actualizarMascotaConFoto(id: number, formData: FormData) {
    return this.http.put<Mascota>(
      `http://localhost:8080/api/mascotas/${id}`,
      formData
    );
  }
}
