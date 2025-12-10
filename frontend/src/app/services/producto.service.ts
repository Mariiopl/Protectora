// producto.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Producto {
  idProducto?: number;
  nombre: string;
  categoria: 'comida' | 'medicamento' | 'limpieza' | 'juguetes' | 'otros';
  cantidad: number;
  unidad: string;
  stockMinimo: number;
}

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private base = 'http://localhost:8080/api/productos';
  constructor(private http: HttpClient) {}

  listar(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.base);
  }
  crear(p: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.base, p);
  }
  actualizar(p: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.base}/${p.idProducto}`, p);
  }
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
