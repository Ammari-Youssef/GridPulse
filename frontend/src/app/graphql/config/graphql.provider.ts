import { ApplicationConfig, inject } from '@angular/core';
import { Apollo, APOLLO_OPTIONS } from 'apollo-angular';
import { HttpLink } from 'apollo-angular/http';
import { InMemoryCache, ApolloClient, ApolloLink } from '@apollo/client/core';
import { environment } from '../../../environments/environment.development';
import { errorLink } from './error-link';
import { authLink } from './auth-link';

export function apolloOptionsFactory(): ApolloClient.Options {
  const httpLink = inject(HttpLink);

  const http = httpLink.create({
    uri: environment.apiUrl,
  });

  const link = ApolloLink.from([
    errorLink, // Error handling global
    authLink, // JWT handling
    http, // Transport HTTP
  ]);


  return {
    cache: new InMemoryCache(),
    link: link,
  };
}

export const graphqlProvider: ApplicationConfig['providers'] = [
  Apollo,
  {
    provide: APOLLO_OPTIONS,
    useFactory: apolloOptionsFactory,
  },
];
