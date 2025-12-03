import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { HELLO_QUERY } from '../../graphql/hello.query';

@Injectable({
  providedIn: 'root',
})
export class HelloService {
  constructor(private apollo: Apollo) {}

  getHello() {
    return this.apollo.watchQuery({
      query: HELLO_QUERY,
    }).valueChanges;
  }

}
