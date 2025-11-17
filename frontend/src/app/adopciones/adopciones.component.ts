import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdopcionService } from '../services/adopcion.service';
import { Adopcion } from '../interfaces/adopcion.model';

@Component({
  selector: 'app-adopciones',
  standalone: true,
  templateUrl: './adopciones.component.html',
  styleUrls: ['./adopciones.component.css'],
  imports: [CommonModule, FormsModule],
})
export class AdopcionesComponent implements OnInit {
  adopciones: Adopcion[] = [];
  cargando = true;

  constructor(private adopcionService: AdopcionService) {}

  ngOnInit(): void {
    this.adopcionService.getMisAdopciones().subscribe({
      next: (res: any) => {
        this.adopciones = res;
        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.cargando = false;
      },
    });
  }
}
