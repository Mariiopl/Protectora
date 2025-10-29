import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';
  private isLoggedInSubject = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private http: HttpClient) {}

  // === AUTENTICACIÓN ===
  login(data: { email: string; contrasena: string }): Observable<any> {
    return new Observable((observer) => {
      this.http.post<any>(`${this.apiUrl}/login`, data).subscribe({
        next: (response) => {
          // Guardamos los datos del usuario
          localStorage.setItem('token', response.token);
          localStorage.setItem('nombre', response.nombre);
          localStorage.setItem('tipoUsuario', response.tipoUsuario);

          this.isLoggedInSubject.next(true);
          observer.next(response);
          observer.complete();
        },
        error: (err) => observer.error(err),
      });
    });
  }

  register(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, data);
  }

  logout(): void {
    localStorage.clear();
    this.isLoggedInSubject.next(false);
  }

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  isAuthenticated(): boolean {
    return this.hasToken();
  }
  // === TOKEN ===
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  isLoggedIn(): Observable<boolean> {
    return this.isLoggedInSubject.asObservable();
  }

  // === DECODIFICAR TOKEN JWT ===
  decodeToken(): any {
    const token = this.getToken();
    if (!token) return null;
    try {
      const decoded: any = jwtDecode(token);
      return decoded;
    } catch (error) {
      console.error('Error decodificando token:', error);
      return null;
    }
  }

  getUsername(): string | null {
    return (
      localStorage.getItem('nombre') || this.decodeToken()?.username || null
    );
  }

  getTipoUsuario(): string | null {
    return (
      localStorage.getItem('tipoUsuario') ||
      this.decodeToken()?.tipoUsuario ||
      null
    );
  }

  // === GUARDAR DATOS DESPUÉS DEL LOGIN ===
  saveAuthData(token: string, username: string, roles: string[]): void {
    localStorage.setItem('token', token);
    localStorage.setItem('username', username);
    localStorage.setItem('roles', JSON.stringify(roles));
  }
}
