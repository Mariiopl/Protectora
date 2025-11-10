export interface Producto {
  idProducto: number;
  nombre: string;
  categoria: 'comida' | 'medicamento' | 'limpieza' | 'juguetes' | 'otros';
  cantidad: number;
  unidad: string;
  stockMinimo?: number;
}
