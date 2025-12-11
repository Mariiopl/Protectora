import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tratamiento, TratamientoDTO } from '../interfaces/tratamiento.model';

export interface Mascota {
  idMascota: number;
  nombre: string;
  foto?: string;
}

@Injectable({
  providedIn: 'root',
})
export class VeterinarioService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getTratamientos(): Observable<Tratamiento[]> {
    return this.http.get<Tratamiento[]>(`${this.baseUrl}/tratamientos`);
  }

  createTratamiento(dto: TratamientoDTO) {
    return this.http.post<Tratamiento>(`${this.baseUrl}/tratamientos`, dto);
  }

  getMascotasAdoptadas(): Observable<Mascota[]> {
    return this.http.get<Mascota[]>(`${this.baseUrl}/mascotas/adoptadas`);
  }
  getTratamientosPorMascota(idMascota: number): Observable<Tratamiento[]> {
    return this.http.get<Tratamiento[]>(
      `${this.baseUrl}/tratamientos/mascota/${idMascota}`
    );
  }
  marcarInformado(id: number) {
    return this.http.put<Tratamiento>(
      `${this.baseUrl}/tratamientos/${id}/informado`,
      {}
    );
  }
  marcarRealizado(id: number) {
    return this.http.put<Tratamiento>(
      `${this.baseUrl}/tratamientos/${id}/realizado`,
      {}
    );
  }
}
