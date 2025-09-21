import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Mascota {
  id: number;
  nombre: string;
  especie: string;
  raza: string;
  edad: number;
  descripcion: string;
  imagenUrl: string;
}

@Injectable({
  providedIn: 'root',
})
export class MascotaService {
  private apiUrl = 'http://localhost:8080/api/mascotas';

  constructor(private http: HttpClient) {}

  getAdoptables(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(`${this.apiUrl}`);
  }
}
