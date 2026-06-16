import { TestBed } from '@angular/core/testing';

import { Saude } from './saude.service';

describe('Saude', () => {
  let service: Saude;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Saude);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
