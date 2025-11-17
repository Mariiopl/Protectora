import { Component, OnInit } from '@angular/core';
import { MascotaService } from '../services/mascota.service';
import { Mascota } from '../interfaces/mascota.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-mascotas-adoptadas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mascotas-adoptables.component.html',
  styleUrls: ['./mascotas-adoptables.component.css'],
})
export class MascotasAdoptablesComponent implements OnInit {
  mascotas: Mascota[] = [];
  cargando = false;

  constructor(private mascotaService: MascotaService) {}

  ngOnInit(): void {
    this.cargarAdoptadas();
  }

  cargarAdoptadas() {
    this.cargando = true;
    this.mascotaService.getAdoptables().subscribe({
      next: (data) => {
        this.mascotas = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar mascotas adoptadas', err);
        this.cargando = false;
      },
    });
  }
  toggleDetalles(mascota: Mascota) {
    mascota.mostrarDetalles = !mascota.mostrarDetalles;
  }
}
