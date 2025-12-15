import { TestBed } from '@angular/core/testing';

import { IdTitleResolver } from './id-title.resolver';

describe('IdTitleResolver', () => {
  let resolver: IdTitleResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(IdTitleResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
