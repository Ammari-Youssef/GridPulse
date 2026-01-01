import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '@core/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  standalone: false,
})
export class LoginComponent {
  form: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
  });

  errorMessage: string | null = null;

  constructor(
    private readonly auth: AuthService,
    private readonly router: Router,
    private readonly snackBar: MatSnackBar
  ) {}

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { email, password } = this.form.value;

    this.errorMessage = null;

    this.auth.login(email, password).subscribe({
      next: () => {
        this.router.navigateByUrl('/');
      },
      error: (err) => {
        const errorMessage = this.parseError(err);
        this.errorMessage = errorMessage;

        this.snackBar.open(errorMessage, 'Close', {
          duration: 5000,
          horizontalPosition: 'right',
          verticalPosition: 'top',
          panelClass: ['snackbar-error'],
        });
      },
    });
  }

  // Convenience getters for easy access to form fields
  get email() {
    return this.form.get('email');
  }

  get password() {
    return this.form.get('password');
  }

  private parseError(err: unknown): string {
    if (err && typeof err === 'object') {
      const e = err as {
        graphQLErrors?: { message: string }[];
        networkError?: { message: string };
        message?: string;
      };

      if (e.graphQLErrors?.length) {
        return e.graphQLErrors.map((err) => err.message).join(', ');
      }

      if (e.networkError) {
        return 'Network error: ' + e.networkError.message;
      }

      if (e.message) {
        return e.message;
      }
    }

    return 'Invalid email or password';
  }
}
