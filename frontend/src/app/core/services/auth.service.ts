import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, of, switchMap, tap } from 'rxjs';

import { Apollo } from 'apollo-angular';

import { AuthenticationResponse } from '@models/interfaces/authentication-response.model';
import { TokenStorageService } from '@services/token-storage.service';
import { User } from '@models/classes/User.model';
import { Role } from '@models/enums/role.enum';

// GraphQL
import { CURRENT_USER_QUERY } from '@graphql/schema/auth/queries/current-user.query';
import { LOGIN_MUTATION } from '@graphql/schema/auth/mutations/login.mutation';
import { LOGOUT_MUTATION } from '@graphql/schema/auth/mutations/logout.mutation';
import { REFRESH_TOKEN_MUTATION } from '@graphql/schema/auth/mutations/refresh-token.mutation';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly userSubject = new BehaviorSubject<User | null>(null);
  readonly user$ = this.userSubject.asObservable();

  constructor(
    private readonly apollo: Apollo,
    private readonly tokenStorage: TokenStorageService
  ) {}

  initAuth(): Observable<User | null> {
  
    const token = this.tokenStorage.access;

    if (!token) {
      this.userSubject.next(null);
      return of(null);
    }

    return this.loadCurrentUser().pipe(
      catchError(() => {
        this.tokenStorage.clear();
        this.userSubject.next(null);
        return of(null);
      })
    );
  }

  login(email: string, password: string): Observable<User> {
    return this.apollo
      .mutate<{ login: AuthenticationResponse }>({
        mutation: LOGIN_MUTATION,
        variables: { email, password },
      })
      .pipe(
        tap(({ data }) => {

          if (!data?.login) {
            throw new Error('Login failed - no data received');
          }

          this.tokenStorage.save(
            data.login.accessToken,
            data.login.refreshToken
          );
        }),
        switchMap(() => this.loadCurrentUser())
      );
  }

  loadCurrentUser(): Observable<User> {
    return this.apollo
      .query<{ getCurrentUser: User }>({
        query: CURRENT_USER_QUERY,
        fetchPolicy: 'network-only',
      })
      .pipe(
        map((res) => {
          return res.data!.getCurrentUser;
        }),
        tap((user) => this.userSubject.next(user))
      );
  }

  logout(): Observable<boolean> {
    return this.apollo
      .mutate<{ logout: boolean }>({
        mutation: LOGOUT_MUTATION,
      })
      .pipe(
        tap(() => {
          this.tokenStorage.clear();
          this.userSubject.next(null);
          this.apollo.client.resetStore();
        }),
        map((res) => res.data?.logout ?? false),
        catchError(() => {
          // Even if server logout fails, clear local state
          this.tokenStorage.clear();
          this.userSubject.next(null);
          this.apollo.client.resetStore();
          return of(false);
        })
      );
  }

  isAuthenticated(): boolean {
    return !!this.userSubject.value;
  }

  getUserRole$(): Observable<Role | null> {
    return this.user$.pipe(map((user) => user?.role ?? null));
  }

  refreshToken(refreshToken: string): Observable<User> {
    return this.apollo
      .mutate<{ refreshToken: AuthenticationResponse }>({
        mutation: REFRESH_TOKEN_MUTATION,
        variables: { refreshToken },
      })
      .pipe(
        tap(({ data }) => {
          if (!data?.refreshToken) {
            throw new Error('Refresh token failed - no data received');
          }

          this.tokenStorage.save(
            data.refreshToken.accessToken,
            data.refreshToken.refreshToken
          );
        }),
        switchMap(() => this.loadCurrentUser())
      );
  }

  get currentUser(): User | null {
    return this.userSubject.value;
  }

  get role(): Role | null {
    return this.userSubject.value?.role ?? null;
  }
}
