import { Component, OnInit } from '@angular/core';
import { HelloService } from '../../core/services/hello.service';
import { JsonPipe } from '@angular/common';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';

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
  data: any = null;
  errorMessage: string | null = null;
  loading = true;

  constructor(
    private helloService: HelloService,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {
    this.loading = true;
    this.errorMessage = null;

    this.helloService.getHello().subscribe({
      next: (result: any) => {
        this.loading = false;
        this.data = result.data;
      },
      error: (err) => {
        this.loading = false;

        let message = this.parseError(err);
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

  private parseError(err: any): string {
    if (err.graphQLErrors?.length) {
      return err.graphQLErrors.map((e: any) => e.message).join(', ');
    }
    if (err.networkError) {
      return 'Network Error : ' + err.networkError.message;
    }
    return 'Unknown Error';
  }
}
