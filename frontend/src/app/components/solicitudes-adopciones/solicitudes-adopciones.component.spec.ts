import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitudesAdopcionesComponent } from './solicitudes-adopciones.component';

describe('SolicitudesAdopcionesComponent', () => {
  let component: SolicitudesAdopcionesComponent;
  let fixture: ComponentFixture<SolicitudesAdopcionesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitudesAdopcionesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SolicitudesAdopcionesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
