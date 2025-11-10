import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Empleado } from '../interfaces/empleado.model';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EmpleadoService {
  private apiUrl = 'http://localhost:8080/api/empleados';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Empleado[]> {
    return this.http
      .get<Empleado[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }
  getById(id: number): Observable<Empleado> {
    return this.http
      .get<Empleado>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }
  create(empleado: Empleado): Observable<Empleado> {
    return this.http
      .post<Empleado>(this.apiUrl, empleado)
      .pipe(catchError(this.handleError));
  }
  update(id: number, empleado: Empleado): Observable<Empleado> {
    return this.http
      .put<Empleado>(`${this.apiUrl}/${id}`, empleado)
      .pipe(catchError(this.handleError));
  }
  delete(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('Error EmpleadoService', error);
    return throwError(() => error);
  }
}
