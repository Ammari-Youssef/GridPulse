import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { HELLO_QUERY } from '../../graphql/hello.query';
import { catchError, throwError, timeout } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class HelloService {
  constructor(private apollo: Apollo) {}

  getHello() {
    return this.apollo
      .watchQuery({
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
