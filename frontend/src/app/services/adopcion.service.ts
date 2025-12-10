import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
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

  getMisAdopciones(token: string): Observable<Adopcion[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http
      .get<Adopcion[]>(`${this.apiUrl}/mis-adopciones`, { headers })
      .pipe(catchError(this.handleError));
  }
  private handleError(error: any) {
    console.error('Error AdopcionService', error);
    return throwError(() => error);
  }

  // ⭐ Crear solicitud de adopción
  crearSolicitudAdopcion(solicitud: Adopcion): Observable<any> {
    return this.http.post(this.apiUrl, solicitud, {
      headers: this.getAuthHeaders(),
    });
  }

  getPendientes(): Observable<Adopcion[]> {
    return this.http.get<Adopcion[]>(`${this.apiUrl}/pendientes`);
  }

  cambiarEstado(id: number, nuevoEstado: string): Observable<void> {
    return this.http.put<void>(
      `${this.apiUrl}/${id}/estado?nuevoEstado=${nuevoEstado}`,
      {}
    );
  }
  getTodasAdopciones(): Observable<Adopcion[]> {
    return this.http.get<Adopcion[]>(`${this.apiUrl}/todas`, {
      headers: this.getAuthHeaders(),
    });
  }
}
