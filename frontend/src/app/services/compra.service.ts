import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Compra } from '../interfaces/compra.model';

@Injectable({
  providedIn: 'root',
})
export class CompraService {
  private apiUrl = 'http://localhost:8080/api/compras';

  constructor(private http: HttpClient) {}

  getCompras(): Observable<Compra[]> {
    return this.http.get<Compra[]>(this.apiUrl);
  }

  getCompra(id: number): Observable<Compra> {
    return this.http.get<Compra>(`${this.apiUrl}/${id}`);
  }

  createCompra(compra: Compra): Observable<Compra> {
    return this.http.post<Compra>(this.apiUrl, compra);
  }

  updateCompra(compra: Compra): Observable<Compra> {
    return this.http.put<Compra>(`${this.apiUrl}/${compra.idCompra}`, compra);
  }

  deleteCompra(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  comprar(c: Compra): Observable<Compra> {
    return this.http.post<Compra>(this.apiUrl, c);
  }
  listarPorProducto(idProducto: number): Observable<Compra[]> {
    return this.http.get<Compra[]>(`${this.apiUrl}/producto/${idProducto}`);
  }
}
export { Compra };
