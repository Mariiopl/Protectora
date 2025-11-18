import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { Adopcion } from '../interfaces/adopcion.model';

@Injectable({
  providedIn: 'root',
})
export class AdopcionService {
  private apiUrl = 'http://localhost:8080/api/adopciones';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();

    if (!token) {
      throw new Error('Usuario no autenticado o token no encontrado');
    }

    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  // ⭐ Obtener las adopciones del usuario autenticado
  getMisAdopciones(): Observable<Adopcion[]> {
    return this.http.get<Adopcion[]>(`${this.apiUrl}/mis-adopciones`, {
      headers: this.getAuthHeaders(),
    });
  }

  // ⭐ Crear solicitud de adopción
  crearSolicitudAdopcion(solicitud: Adopcion): Observable<any> {
    return this.http.post(this.apiUrl, solicitud, {
      headers: this.getAuthHeaders(),
    });
  }
}
