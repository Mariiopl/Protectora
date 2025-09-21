import { Component, OnInit } from '@angular/core';
import { MascotaService, Mascota } from '../services/mascota.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-catalogo',
  templateUrl: './catalogo.component.html',
  styleUrls: ['./catalogo.component.css'],
  imports: [CommonModule],
  standalone: true,
})
export class CatalogoComponent implements OnInit {
  mascotas: Mascota[] = [];
  cargando = true;

  constructor(private mascotaService: MascotaService) {}

  ngOnInit(): void {
    this.mascotaService.getAdoptables().subscribe({
      next: (data) => {
        this.mascotas = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error cargando mascotas', err);
        this.cargando = false;
      },
    });
  }
}
