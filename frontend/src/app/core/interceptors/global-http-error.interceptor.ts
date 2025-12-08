import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { retry, tap } from 'rxjs';

import { MatSnackBar } from '@angular/material/snack-bar';

export const globalHttpErrorInterceptor: HttpInterceptorFn = (req, next) => {
  
  const snackBar = inject(MatSnackBar)

  return next(req).pipe(
    retry({count: 3, delay: 1000}),
    tap({
      error: (err: HttpErrorResponse)=>{
        snackBar.open(err.message, 'Close',{
          duration: 5000,
        })
      }
    }),

  )
};
