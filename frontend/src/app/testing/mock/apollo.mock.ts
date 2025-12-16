import { of } from 'rxjs';

export const ApolloMock = {
  watchQuery: () => ({ valueChanges: of({ data: {} }) }),
  mutate: () => of({ data: {} }),
};
