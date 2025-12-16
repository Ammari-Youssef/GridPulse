import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { TokenStorageService } from '@core/services/token-storage.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private tokenService: TokenStorageService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    _state: RouterStateSnapshot
  ): boolean | ReturnType<Router['parseUrl']> {
    const isLogged = this.tokenService.isAuthenticated();
    const userRole = this.tokenService.getUserRole();
    const requiredRole = route.data?.['role'];

    if (!isLogged) {
      return this.router.parseUrl('/login');
    }

    if (requiredRole && userRole !== requiredRole) {
      return this.router.parseUrl('/forbidden');
    }

    return true;
  }
}
