import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { HELLO_QUERY } from '../../graphql/hello.query';
import { catchError, throwError } from 'rxjs';
import { ApiStatusResponse } from '../models/interfaces/api-status-response';

@Injectable({
  providedIn: 'root',
})
export class ApiStatusService {
  constructor(private apollo: Apollo) {}

  getHello() {
    return this.apollo
      .watchQuery<ApiStatusResponse>({
        query: HELLO_QUERY,
        errorPolicy: 'all',
      })
      .valueChanges.pipe(
        catchError((err) => {
          console.error('Apollo error (service):', err);
          return throwError(() => err);
        })
      );
  }
}
