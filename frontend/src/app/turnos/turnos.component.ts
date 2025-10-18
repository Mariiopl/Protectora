import { Component, OnInit } from '@angular/core';
import { TurnoService, Turno } from '../services/turno.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-turnos',
  templateUrl: './turnos.component.html',
  styleUrls: ['./turnos.component.css'],
  imports: [CommonModule],
  standalone: true,
})
export class TurnosComponent implements OnInit {
  turnos: Turno[] = [];
  error: string | null = null;

  constructor(private turnoService: TurnoService) {}

  ngOnInit(): void {
    this.cargarTurnos();
  }

  cargarTurnos() {
    this.turnoService.getTurnos().subscribe({
      next: (data) => (this.turnos = data),
      error: (err) => {
        console.error('Error cargando turnos', err);
        this.error = 'No se pudieron cargar los turnos.';
      },
    });
  }

  eliminarTurno(id: number) {
    if (!confirm('¿Seguro que quieres eliminar este turno?')) return;

    this.turnoService.deleteTurno(id).subscribe({
      next: () => {
        this.turnos = this.turnos.filter((t) => t.idTurno !== id);
      },
      error: (err) => {
        console.error('Error eliminando turno', err);
        alert('No se pudo eliminar el turno.');
      },
    });
  }
}
