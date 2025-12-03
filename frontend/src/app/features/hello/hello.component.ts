import { Component, OnInit } from '@angular/core';
import { HelloService } from '../../core/services/hello.service';
import { JsonPipe } from '@angular/common';

@Component({
  selector: 'app-hello',
  imports: [JsonPipe],
  templateUrl: './hello.component.html',
  styleUrl: './hello.component.scss',
})
export class HelloComponent implements OnInit {
  data: any;
  errorMessage: string | null = null;

  constructor(private helloService: HelloService) {}

  ngOnInit(): void {
    this.helloService.getHello().subscribe({
      next: (result: any) => {
        this.data = result.data;
        console.log('Apollo Response:', result);
      },
      error: (err) => {
        console.error('GraphQL or network error', err);
        this.errorMessage = this.parseError(err);
      },
    });
  }

  private parseError(err: any): string {
    // Apollo errors have graphQLErrors and networkError
    if (err.graphQLErrors?.length) {
      return err.graphQLErrors.map((e: any) => e.message).join(', ');
    } else if (err.networkError) {
      return 'Network error: ' + err.networkError.message;
    }
    return 'Unknown error';
  }
}