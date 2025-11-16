import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MascotasAdoptablesComponent } from './mascotas-adoptables.component';

describe('MascotasAdoptablesComponent', () => {
  let component: MascotasAdoptablesComponent;
  let fixture: ComponentFixture<MascotasAdoptablesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MascotasAdoptablesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(MascotasAdoptablesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
