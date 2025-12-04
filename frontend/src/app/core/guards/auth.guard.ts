import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { TokenStorageService } from '../services/token-storage.service';


export const authGuard: CanActivateFn = (route, state) => {
  const tokenService = inject(TokenStorageService);
  const router = inject(Router);

  const isLogged = tokenService.isAuthenticated();
  const userRole = tokenService.getUserRole(); 
  const requiredRole = route.data?.['role'];

  if (!isLogged) {
    return router.parseUrl('/login');
  }

  // Forbidden
  if (requiredRole && userRole !== requiredRole) {
    return router.parseUrl('/forbidden');
  }

  return true;
};
