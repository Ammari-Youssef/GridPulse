import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { TokenStorageService } from '@core/services/token-storage.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {

  constructor(
    private tokenService: TokenStorageService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot
  ): boolean | ReturnType<Router['parseUrl']> {
    const expectedRole = route.data['role'] as 'ADMIN' | 'USER';
    const userRole = this.tokenService.getUserRole();

    if (userRole === expectedRole) {
      return true;
    }

    return this.router.parseUrl('/forbidden');
  }
}
