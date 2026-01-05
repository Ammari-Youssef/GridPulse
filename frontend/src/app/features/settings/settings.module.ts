import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileSettingsComponent } from './profile-settings/profile-settings.component';
import { SettingsComponent } from './settings/settings.component';
import { SecuritySettingsComponent } from './security-settings/security-settings.component';
import { UserManagementComponent } from './user-management/user-management.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '@shared/shared.module';

@NgModule({
  declarations: [
    SettingsComponent,
    ProfileSettingsComponent,
    SecuritySettingsComponent,
    UserManagementComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
  ],
})
export class SettingsModule {}

