import { TestBed } from '@angular/core/testing';

import { TareaasignadaService } from './tareaasignada.service';

describe('TareaasignadaService', () => {
  let service: TareaasignadaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TareaasignadaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
