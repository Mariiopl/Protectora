export interface Compra {
  idCompra: number;
  idProducto: number;
  proveedor?: string;
  fecha: string; // ISO date
  cantidad: number;
  costoTotal: number;
}
