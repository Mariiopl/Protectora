import { TestBed } from '@angular/core/testing';

import { AdopcionService } from './adopcion.service';

describe('AdopcionService', () => {
  let service: AdopcionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdopcionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
