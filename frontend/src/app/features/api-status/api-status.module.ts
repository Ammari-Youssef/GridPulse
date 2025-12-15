import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiStatusComponent } from './api-status.component';

import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [ApiStatusComponent],
  imports: [
    CommonModule,

    // Angular Material Modules
    MatProgressSpinnerModule,
    MatCardModule,
    MatSnackBarModule,
    MatButtonModule,
  ],
  exports: [ApiStatusComponent],
})
export class ApiStatusModule {}
