import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';
import { TokenStorageService } from '../services/token-storage.service';

export const httpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const tokenService = inject(TokenStorageService);

  return next(req).pipe(
    catchError((err) => {
      const status = err?.status;
      console.error('[HTTP ERROR INTERCEPTOR]', status, err);

      if (status === 401) {
        tokenService.clearToken();
        router.navigate(['/login']);
      } else if (status === 403) {
        router.navigate(['/forbidden']);
      }

      return throwError(() => err);
    })
  );
};
