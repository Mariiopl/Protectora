import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { TareaAsignada } from '../interfaces/tareaasignada.model';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TareaAsignadaService {
  private apiUrl = 'http://localhost:8080/api/tareasasignadas';

  constructor(private http: HttpClient) {}

  getAllTareasAsignadas(): Observable<TareaAsignada[]> {
    return this.http
      .get<TareaAsignada[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }

  getTareaAsignadaById(id: number): Observable<TareaAsignada> {
    return this.http
      .get<TareaAsignada>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  createTareaAsignada(tarea: TareaAsignada): Observable<TareaAsignada> {
    return this.http
      .post<TareaAsignada>(this.apiUrl, tarea)
      .pipe(catchError(this.handleError));
  }

  updateTareaAsignada(
    id: number,
    tarea: TareaAsignada
  ): Observable<TareaAsignada> {
    return this.http
      .put<TareaAsignada>(`${this.apiUrl}/${id}`, tarea)
      .pipe(catchError(this.handleError));
  }

  deleteTareaAsignada(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('Error TareaAsignadaService', error);
    return throwError(() => error);
  }
}
