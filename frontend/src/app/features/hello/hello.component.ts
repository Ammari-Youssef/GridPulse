import { Component, inject, OnInit } from '@angular/core';
import { HelloService } from '../../core/services/hello.service';
import { JsonPipe } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';
import { HelloResponse } from '../../core/models/interfaces/HelloResponse';
import { ObservableQuery } from '@apollo/client';

@Component({
  selector: 'app-hello',
  imports: [
    JsonPipe,
    MatProgressSpinnerModule,
    MatCardModule,
    MatSnackBarModule,
    MatButtonModule,
  ],
  templateUrl: './hello.component.html',
  styleUrl: './hello.component.scss',
})
export class HelloComponent implements OnInit {
  data: HelloResponse | null | undefined = null;
  errorMessage: string | null = null;
  loading = true;

  helloService = inject(HelloService);
  snackbar = inject(MatSnackBar);

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.loading = true;
    this.errorMessage = null;

    this.helloService.getHello().subscribe({
      next: (result: ObservableQuery.Result<HelloResponse>) => {
        this.loading = false;
        this.data = result.data as HelloResponse;
      },
      error: (err) => {
        this.loading = false;

        const message = this.parseError(err);
        this.errorMessage = message;

        this.snackbar.open(message, 'Close', {
          duration: 6000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
          panelClass: ['snackbar-error'],
        });
      },
    });
  }

  retry() {
    this.loadData();
  }

  private parseError(err: unknown): string {
    // Check if err is an object (not null)
    if (err && typeof err === 'object') {
      // Cast to a known structure
      const e = err as {
        graphQLErrors?: { message: string }[];
        networkError?: { message: string };
      };

      if (e.graphQLErrors?.length) {
        return e.graphQLErrors.map((err) => err.message).join(', ');
      }

      if (e.networkError) {
        return 'Network Error: ' + e.networkError.message;
      }
    }

    return 'Unknown Error';
  }
}
