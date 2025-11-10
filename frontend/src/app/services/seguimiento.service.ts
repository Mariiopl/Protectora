import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Seguimiento } from '../interfaces/seguimiento.model';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SeguimientoService {
  private apiUrl = 'http://localhost:8080/api/seguimientos';

  constructor(private http: HttpClient) {}

  getAllSeguimientos(): Observable<Seguimiento[]> {
    return this.http
      .get<Seguimiento[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }

  getSeguimientoById(id: number): Observable<Seguimiento> {
    return this.http
      .get<Seguimiento>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  createSeguimiento(seguimiento: Seguimiento): Observable<Seguimiento> {
    return this.http
      .post<Seguimiento>(this.apiUrl, seguimiento)
      .pipe(catchError(this.handleError));
  }

  updateSeguimiento(
    id: number,
    seguimiento: Seguimiento
  ): Observable<Seguimiento> {
    return this.http
      .put<Seguimiento>(`${this.apiUrl}/${id}`, seguimiento)
      .pipe(catchError(this.handleError));
  }

  deleteSeguimiento(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('Error SeguimientoService', error);
    return throwError(() => error);
  }
}
