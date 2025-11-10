import { TestBed } from '@angular/core/testing';

import { ApadrinamientoService } from './apadrinamiento.service';

describe('ApadrinamientoService', () => {
  let service: ApadrinamientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ApadrinamientoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
