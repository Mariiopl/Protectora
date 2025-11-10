import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Donacion } from '../interfaces/donacion.model';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class DonacionService {
  private apiUrl = 'http://localhost:8080/api/donaciones';

  constructor(private http: HttpClient) {}

  getAllDonaciones(): Observable<Donacion[]> {
    return this.http
      .get<Donacion[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }

  getDonacionById(id: number): Observable<Donacion> {
    return this.http
      .get<Donacion>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  createDonacion(donacion: Donacion): Observable<Donacion> {
    return this.http
      .post<Donacion>(this.apiUrl, donacion)
      .pipe(catchError(this.handleError));
  }

  updateDonacion(id: number, donacion: Donacion): Observable<Donacion> {
    return this.http
      .put<Donacion>(`${this.apiUrl}/${id}`, donacion)
      .pipe(catchError(this.handleError));
  }

  deleteDonacion(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('Error DonacionService', error);
    return throwError(() => error);
  }
}
