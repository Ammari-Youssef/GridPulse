import { of } from 'rxjs';

export const ApolloMock = {
  query: () => ({ valueChanges: of({ data: {} }) }),
  watchQuery: () => ({ valueChanges: of({ data: {} }) }),
  mutate: () => of({ data: {} }),
};
