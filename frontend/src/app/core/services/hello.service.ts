import { inject, Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { HELLO_QUERY } from '../../graphql/hello.query';
import { catchError, throwError } from 'rxjs';
import { HelloResponse } from '../models/interfaces/HelloResponse';

@Injectable({
  providedIn: 'root',
})
export class HelloService {
  private readonly apollo = inject(Apollo);

  getHello() {
    return this.apollo
      .watchQuery<HelloResponse>({
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
