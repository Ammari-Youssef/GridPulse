import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { filter, map, Observable, take } from 'rxjs';

import { AuthService } from '@core/services/auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  canActivate(): Observable<boolean | UrlTree> {
    return this.authService.user$.pipe(
      filter((user) => user !== null),
      take(1),
      map((user) => (user ? true : this.router.parseUrl('/login')))
    );
  }
}
