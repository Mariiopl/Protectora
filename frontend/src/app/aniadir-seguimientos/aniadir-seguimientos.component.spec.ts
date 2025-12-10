import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AniadirSeguimientosComponent } from './aniadir-seguimientos.component';

describe('AniadirSeguimientosComponent', () => {
  let component: AniadirSeguimientosComponent;
  let fixture: ComponentFixture<AniadirSeguimientosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AniadirSeguimientosComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AniadirSeguimientosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
