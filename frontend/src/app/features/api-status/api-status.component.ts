import { Component, OnInit } from '@angular/core';
import { ApiStatusService } from '../../core/services/api-status.service';

import { MatSnackBar } from '@angular/material/snack-bar';
import { ApiStatusResponse } from '../../core/models/interfaces/api-status-response';
import { ObservableQuery } from '@apollo/client';

@Component({
  selector: 'app-api-status',
  standalone: false,
  templateUrl: './api-status.component.html',
  styleUrl: './api-status.component.scss',
})
export class ApiStatusComponent implements OnInit {
  data: ApiStatusResponse | null | undefined = null;
  errorMessage: string | null = null;
  loading = true;

  constructor(
    private apiStatusService: ApiStatusService,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.loading = true;
    this.errorMessage = null;

    this.apiStatusService.getHello().subscribe({
      next: (result: ObservableQuery.Result<ApiStatusResponse>) => {
        this.loading = false;
        this.data = result.data as ApiStatusResponse;
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
