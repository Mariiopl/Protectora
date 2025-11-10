import { TestBed } from '@angular/core/testing';

import { HorastrabajadasService } from './horastrabajadas.service';

describe('HorastrabajadasService', () => {
  let service: HorastrabajadasService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HorastrabajadasService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
