import { TestBed } from '@angular/core/testing';

import { AnalyticsService } from './analytics.service';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '@testing/mock/apollo.mock';

describe('AnalyticsService', () => {
  let service: AnalyticsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: Apollo, useValue: ApolloMock}]
    });
    service = TestBed.inject(AnalyticsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
