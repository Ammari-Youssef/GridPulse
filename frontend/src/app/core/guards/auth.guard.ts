import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from '@angular/router';
import { AuthService } from '@services/auth.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    console.log('🛡️ AuthGuard: Checking authentication');

    const isAuthenticated = this.authService.isAuthenticated();

    if (isAuthenticated) {
      console.log('✅ AuthGuard: User authenticated, allowing access');
      return true;
    }

    console.log('❌ AuthGuard: Not authenticated, redirecting to login');
    this.router.navigate(['/login'], {
      queryParams: { returnUrl: state.url },
    });
    return false;
  }
}
