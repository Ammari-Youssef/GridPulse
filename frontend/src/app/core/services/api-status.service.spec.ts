import { TestBed } from '@angular/core/testing';

import { ApiStatusService } from './api-status.service';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '@testing/mock/apollo.mock';

describe('ApiStatusService', () => {
  let service: ApiStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: Apollo, useValue: ApolloMock }],
    });

    service = TestBed.inject(ApiStatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
