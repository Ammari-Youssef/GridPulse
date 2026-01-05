// features/settings/profile-settings/profile-settings.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '@core/services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-profile-settings',
  templateUrl: './profile-settings.component.html',
  styleUrls: ['./profile-settings.component.scss'],
  standalone: false,
})
export class ProfileSettingsComponent implements OnInit {
  profileForm: FormGroup;
  loading = false;

  constructor(
    private readonly fb: FormBuilder,
    private readonly authService: AuthService,
    private readonly snackBar: MatSnackBar
  ) {
    this.profileForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: [{ value: '', disabled: true }], // Email read-only
      role: [{ value: '', disabled: true }], // Role read-only
    });
  }

  ngOnInit() {
    this.loadUserProfile();
  }

  loadUserProfile() {
    this.authService.user$.subscribe((user) => {
      if (user) {
        this.profileForm.patchValue({
          firstname: user.firstname,
          lastname: user.lastname,
          email: user.email,
          role: user.role,
        });
      }
    });
  }

  onSave() {
    if (this.profileForm.invalid) {
      this.snackBar.open('Please fill all required fields', 'Close', {
        duration: 3000,
      });
      return;
    }

    this.loading = true;

    // TODO: Implement GraphQL mutation to update profile
    setTimeout(() => {
      this.loading = false;
      this.snackBar.open('Profile updated successfully', 'Close', {
        duration: 3000,
      });
    }, 1000);
  }

  onCancel() {
    this.loadUserProfile();
  }

  getRoleBadgeColor(): string {
    const role = this.profileForm.get('role')?.value;
    return role === 'ADMIN' ? 'bg-blue-600' : 'bg-purple-600';
  }

  getRoleLabel(): string {
    const role = this.profileForm.get('role')?.value;
    return role === 'ADMIN' ? 'Administrator / Operator' : 'User';
  }
}
