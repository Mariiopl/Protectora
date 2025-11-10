import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mascota } from '../interfaces/mascota';

@Injectable({
  providedIn: 'root',
})
export class MascotaService {
  private apiUrl = 'http://localhost:8080/api/mascotas'; // 🔗 Ajusta si tu backend usa otro prefijo

  constructor(private http: HttpClient) {}

  // Obtener mascotas adoptables
  getAdoptables(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(this.apiUrl);
  }

  // Actualizar una mascota
  actualizarMascota(mascota: Mascota): Observable<Mascota> {
    return this.http.put<Mascota>(
      `${this.apiUrl}/${mascota.idMascota}`,
      mascota
    );
  }

  // Obtener una mascota por ID
  getMascotaById(id: number): Observable<Mascota> {
    return this.http.get<Mascota>(`${this.apiUrl}/${id}`);
  }
}
