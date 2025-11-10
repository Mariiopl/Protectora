import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Apadrinamiento } from '../interfaces/apadrinamiento.model';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ApadrinamientoService {
  private apiUrl = 'http://localhost:8080/api/apadrinamientos';

  constructor(private http: HttpClient) {}

  getAllApadrinamientos(): Observable<Apadrinamiento[]> {
    return this.http
      .get<Apadrinamiento[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }

  getApadrinamientoById(id: number): Observable<Apadrinamiento> {
    return this.http
      .get<Apadrinamiento>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  createApadrinamiento(
    apadrinamiento: Apadrinamiento
  ): Observable<Apadrinamiento> {
    return this.http
      .post<Apadrinamiento>(this.apiUrl, apadrinamiento)
      .pipe(catchError(this.handleError));
  }

  updateApadrinamiento(
    id: number,
    apadrinamiento: Apadrinamiento
  ): Observable<Apadrinamiento> {
    return this.http
      .put<Apadrinamiento>(`${this.apiUrl}/${id}`, apadrinamiento)
      .pipe(catchError(this.handleError));
  }

  deleteApadrinamiento(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any) {
    console.error('Error ApadrinamientoService', error);
    return throwError(() => error);
  }
}
