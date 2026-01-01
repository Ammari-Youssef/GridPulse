import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { HELLO_QUERY } from '@graphql/hello.query';
import { catchError, throwError } from 'rxjs';
import { ApiStatusResponse } from '@core/models/interfaces/api-status-response';

@Injectable({
  providedIn: 'root',
})
export class ApiStatusService {
  constructor(private readonly apollo: Apollo) {}

  getHello() {
    return this.apollo
      .watchQuery<ApiStatusResponse>({
        query: HELLO_QUERY,
        errorPolicy: 'all',
      })
      .valueChanges.pipe(
        catchError((err) => {
          return throwError(() => err);
        })
      );
  }
}
