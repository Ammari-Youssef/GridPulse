import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router, UrlTree } from '@angular/router';
import { filter, map, Observable, take } from 'rxjs';

import { Role } from '@core/models/enums/role.enum';
import { AuthService } from '@core/services/auth.service';


@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot): Observable<boolean | UrlTree> {
    const expectedRole = route.data['role'] as Role;

    return this.authService.user$.pipe(
      filter((user) => !!user),
      take(1),
      map((user) =>
        user.role === expectedRole ? true : this.router.parseUrl('/forbidden')
      )
    );
  }
}
