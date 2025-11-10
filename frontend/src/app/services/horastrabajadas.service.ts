import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { HorasTrabajadas } from '../interfaces/horastrabajadas.model';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class HorasTrabajadasService {
  private apiUrl = 'http://localhost:8080/api/horastrabajadas';

  constructor(private http: HttpClient) {}

  getAllHoras(): Observable<HorasTrabajadas[]> {
    return this.http
      .get<HorasTrabajadas[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }

  getHorasById(id: number): Observable<HorasTrabajadas> {
    return this.http
      .get<HorasTrabajadas>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  createHoras(horas: HorasTrabajadas): Observable<HorasTrabajadas> {
    return this.http
      .post<HorasTrabajadas>(this.apiUrl, horas)
      .pipe(catchError(this.handleError));
  }

  updateHoras(id: number, horas: HorasTrabajadas): Observable<HorasTrabajadas> {
    return this.http
      .put<HorasTrabajadas>(`${this.apiUrl}/${id}`, horas)
      .pipe(catchError(this.handleError));
  }

  deleteHoras(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('Error HorasTrabajadasService', error);
    return throwError(() => error);
  }
}
