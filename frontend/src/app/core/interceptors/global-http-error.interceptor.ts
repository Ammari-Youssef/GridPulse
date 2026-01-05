import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Router } from '@angular/router';
import { TokenStorageService } from '@core/services/token-storage.service';
import { SnackbarService } from '@shared/services/snackbar.service';

@Injectable()
export class GlobalHttpErrorInterceptor implements HttpInterceptor {
  constructor(
    private readonly router: Router,
    private readonly tokenService: TokenStorageService,
    private readonly snackbar: SnackbarService
  ) {}

  intercept(
    req: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    return next.handle(req).pipe(
      // retry transient errors (network hiccups, 5xx) up to 3 times
      retry({ count: 3, delay: 1000 }),
      catchError((err: HttpErrorResponse) => {
        const status = err?.status;

        if (status === 401) {
          this.tokenService.clear();
          this.snackbar.showError('Session expired. Please log in again.');
          this.router.navigate(['/login']);
        } else if (status === 403) {
          this.snackbar.showError('Access denied.');
          this.router.navigate(['/forbidden']);
        } else {
          this.snackbar.showError(
            err.message || 'An unexpected error occurred.'
          );
        }

        return throwError(() => err);
      })
    );
  }
}
