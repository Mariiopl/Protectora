import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service'; // importa tu servicio de autenticación

@Injectable({
  providedIn: 'root',
})
export class AdopcionService {
  private apiUrl = 'http://localhost:8080/api/adopciones';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getMisAdopciones(): Observable<any> {
    const token = this.authService.getToken(); // JWT guardado en localStorage o donde lo tengas
    if (!token)
      throw new Error(
        'Usuario no autenticado o idUsuario no encontrado en el token'
      );

    return this.http.get(`${this.apiUrl}/mis-adopciones`, {
      headers: { Authorization: `Bearer ${token}` },
    });
  }
}
