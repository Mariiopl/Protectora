import { Component, OnInit } from '@angular/core';
import { TratamientoService } from '../services/tratamiento.service';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MascotaService } from '../services/mascota.service';
import { EmpleadoService } from '../services/empleado.service';
import { Tratamiento, TratamientoDTO } from '../interfaces/tratamiento.model';
import { Mascota } from '../interfaces/mascota.model';
import { Empleado } from '../interfaces/empleado.model';

@Component({
  standalone: true,
  imports: [ReactiveFormsModule],
  selector: 'app-tratamiento',
  templateUrl: './tratamiento.component.html',
  styleUrls: ['./tratamiento.component.css'],
})
export class TratamientoComponent implements OnInit {
  tratamientos: Tratamiento[] = [];
  mascotas: Mascota[] = [];
  veterinarios: Empleado[] = [];
  form: FormGroup;
  tipos = ['vacuna', 'desparasitación', 'cirugía', 'otro'];
  editMode = false;
  editId: number | null = null;

  constructor(
    private tratamientoService: TratamientoService,
    private mascotaService: MascotaService,
    private empleadoService: EmpleadoService,
    private fb: FormBuilder
  ) {
    this.form = this.fb.group({
      idMascota: [null, Validators.required],
      tipo: [null, Validators.required],
      descripcion: ['', [Validators.required, Validators.maxLength(500)]],
      fecha: [null, Validators.required],
      idVeterinario: [null, Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadTratamientos();
    this.loadMascotas();
    this.loadVeterinarios();
  }

  loadTratamientos() {
    this.tratamientoService
      .getTratamientos()
      .subscribe((data) => (this.tratamientos = data));
  }

  loadMascotas() {
    this.mascotaService
      .getAdoptables()
      .subscribe((data) => (this.mascotas = data));
  }

  loadVeterinarios() {
    this.empleadoService
      .getVeterinarios()
      .subscribe((data) => (this.veterinarios = data));
  }

  submit() {
    const dto: TratamientoDTO = this.form.value;

    if (this.editMode && this.editId) {
      this.tratamientoService
        .updateTratamiento(this.editId, dto)
        .subscribe(() => {
          this.loadTratamientos();
          this.resetForm();
        });
    } else {
      this.tratamientoService.createTratamiento(dto).subscribe(() => {
        this.loadTratamientos();
        this.resetForm();
      });
    }
  }

  edit(tratamiento: Tratamiento) {
    this.editMode = true;
    this.editId = tratamiento.idTratamiento ?? null;
    this.form.patchValue({
      idMascota: tratamiento.mascota.idMascota,
      tipo: tratamiento.tipo,
      descripcion: tratamiento.descripcion,
      fecha: tratamiento.fecha,
      idVeterinario: tratamiento.veterinario,
    });
  }

  delete(id: number) {
    this.tratamientoService
      .deleteTratamiento(id)
      .subscribe(() => this.loadTratamientos());
  }

  resetForm() {
    this.form.reset();
    this.editMode = false;
    this.editId = null;
  }
}
