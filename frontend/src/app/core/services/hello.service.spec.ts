import { TestBed } from '@angular/core/testing';

import { HelloService } from './hello.service';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '../test/mock/apollo.mock';

describe('HelloService', () => {
  let service: HelloService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: Apollo, useValue: ApolloMock }],
    });

    service = TestBed.inject(HelloService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});