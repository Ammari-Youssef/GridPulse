import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-security-settings',
  templateUrl: './security-settings.component.html',
  styleUrl: './security-settings.component.scss',
  standalone: false,
})
export class SecuritySettingsComponent {
  passwordForm: FormGroup;
  loading = false;
  hideCurrentPassword = true;
  hideNewPassword = true;
  hideConfirmPassword = true;

  constructor(private readonly fb: FormBuilder, private readonly snackBar: MatSnackBar) {
    this.passwordForm = this.fb.group(
      {
        currentPassword: ['', Validators.required],
        newPassword: ['', [Validators.required, Validators.minLength(8)]],
        confirmPassword: ['', Validators.required],
      },
      { validators: this.passwordMatchValidator }
    );
  }

  passwordMatchValidator(form: FormGroup) {
    const newPassword = form.get('newPassword')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return newPassword === confirmPassword ? null : { passwordMismatch: true };
  }

  onChangePassword() {
    if (this.passwordForm.invalid) {
      this.snackBar.open('Please fill all fields correctly', 'Close', {
        duration: 3000,
      });
      return;
    }

    this.loading = true;

    // TODO: Implement GraphQL mutation
    setTimeout(() => {
      this.loading = false;
      this.passwordForm.reset();
      this.snackBar.open('Password changed successfully', 'Close', {
        duration: 3000,
      });
    }, 1000);
  }
}
