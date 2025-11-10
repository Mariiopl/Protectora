import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Adopcion } from '../interfaces/adopcion.model';

@Injectable({
  providedIn: 'root',
})
export class AdopcionService {
  private apiUrl = 'http://localhost:8080/api/adopciones';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Adopcion[]> {
    return this.http.get<Adopcion[]>(this.apiUrl);
  }

  getById(id: number): Observable<Adopcion> {
    return this.http.get<Adopcion>(`${this.apiUrl}/${id}`);
  }

  create(adopcion: Adopcion): Observable<Adopcion> {
    return this.http.post<Adopcion>(this.apiUrl, adopcion);
  }

  update(adopcion: Adopcion): Observable<Adopcion> {
    return this.http.put<Adopcion>(
      `${this.apiUrl}/${adopcion.idAdopcion}`,
      adopcion
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
