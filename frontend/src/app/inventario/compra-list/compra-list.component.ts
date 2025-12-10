// import { Component, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { CompraService } from '../../services/compra.service';
// import { FormsModule } from '@angular/forms';
// import { Compra } from '../../interfaces/compra.model';

// @Component({
//   selector: 'app-compra-list',
//   standalone: true,
//   imports: [CommonModule, FormsModule],
//   templateUrl: './compra-list.component.html',
//   styleUrls: ['./compra-list.component.css'],
// })
// export class CompraListComponent implements OnInit {
//   compras: Compra[] = [];
//   cargando = true;

//   constructor(private compraService: CompraService) {}

//   ngOnInit(): void {
//     this.cargarCompras();
//   }

//   cargarCompras() {
//     this.cargando = true;
//     this.compraService.getCompras().subscribe({
//       next: (data) => {
//         this.compras = data;
//         this.cargando = false;
//       },
//       error: (err) => {
//         console.error(err);
//         this.cargando = false;
//       },
//     });
//   }

//   eliminarCompra(id: number) {
//     this.compraService.deleteCompra(id).subscribe(() => this.cargarCompras());
//   }
// }
