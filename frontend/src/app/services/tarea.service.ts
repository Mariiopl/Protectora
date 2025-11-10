import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Tarea } from '../interfaces/tarea.model';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TareaService {
  private apiUrl = 'http://localhost:8080/api/tareas';

  constructor(private http: HttpClient) {}

  getAllTareas(): Observable<Tarea[]> {
    return this.http
      .get<Tarea[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }

  getTareaById(id: number): Observable<Tarea> {
    return this.http
      .get<Tarea>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  createTarea(tarea: Tarea): Observable<Tarea> {
    return this.http
      .post<Tarea>(this.apiUrl, tarea)
      .pipe(catchError(this.handleError));
  }

  updateTarea(id: number, tarea: Tarea): Observable<Tarea> {
    return this.http
      .put<Tarea>(`${this.apiUrl}/${id}`, tarea)
      .pipe(catchError(this.handleError));
  }

  deleteTarea(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('Error TareaService', error);
    return throwError(() => error);
  }
}
