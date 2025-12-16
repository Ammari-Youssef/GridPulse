import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '@core/models/interfaces/authentication-response.model';
import { environment } from '@environments/environment';
import { Apollo } from 'apollo-angular';
import { LOGIN_MUTATION } from '@graphql/schema/auth/mutations/login.mutation';
import { ApolloClient } from '@apollo/client';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_URL = environment.apiUrl;

  constructor(private apollo: Apollo) {}

  login(
    email: string,
    password: string
  ): Observable<ApolloClient.MutateResult<AuthenticationResponse>> {
    return this.apollo.mutate<AuthenticationResponse>({
      mutation: LOGIN_MUTATION,
      variables: { email, password },
    });
  }
}
