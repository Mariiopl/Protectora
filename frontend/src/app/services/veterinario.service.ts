import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Tratamiento, TratamientoDTO } from '../interfaces/tratamiento.model';

@Injectable({
  providedIn: 'root',
})
export class VeterinarioService {
  private apiUrl = 'http://localhost:8080/api/tratamientos';

  constructor(private http: HttpClient) {}

  getTratamientos(): Observable<Tratamiento[]> {
    return this.http.get<Tratamiento[]>(`${this.apiUrl}`);
  }

  createTratamiento(dto: TratamientoDTO): Observable<Tratamiento> {
    return this.http.post<Tratamiento>(`${this.apiUrl}`, dto);
  }

  getTratamientoById(id: number): Observable<Tratamiento> {
    return this.http.get<Tratamiento>(`${this.apiUrl}/${id}`);
  }

  updateTratamiento(id: number, dto: TratamientoDTO): Observable<Tratamiento> {
    return this.http.put<Tratamiento>(`${this.apiUrl}/${id}`, dto);
  }

  deleteTratamiento(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
