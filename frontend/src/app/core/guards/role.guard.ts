import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { TokenStorageService } from '../services/token-storage.service';

export const roleGuard: CanActivateFn = (route, state) => {
  void state;
  
  const tokenService = inject(TokenStorageService);
  const router = inject(Router);

  const expectedRole = route.data['role'] as 'ADMIN' | 'USER';
  const userRole = tokenService.getUserRole();

  if (userRole === expectedRole) {
    return true;
  }

  return router.parseUrl('/forbidden');
};
