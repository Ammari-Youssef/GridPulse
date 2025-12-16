import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { TokenStorageService } from '@core/services/token-storage.service';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
  constructor(
    private router: Router,
    private tokenService: TokenStorageService
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((err) => {
        const status = err?.status;
        console.error('[HTTP ERROR INTERCEPTOR]', status, err);

        if (status === 401) {
          this.tokenService.clearToken();
          this.router.navigate(['/login']);
        } else if (status === 403) {
          this.router.navigate(['/forbidden']);
        }

        return throwError(() => err);
      })
    );
  }
}
