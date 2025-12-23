import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

// Shared Components & Modules
import { MaterialModule } from '@shared/ui/material/material.module';
import { ForbiddenComponent } from '@shared/pages/forbidden/forbidden.component';
import { NotFoundComponent } from '@shared/pages/not-found/not-found.component';

@NgModule({
  declarations: [ForbiddenComponent, NotFoundComponent],
  imports: [
    CommonModule,
    RouterModule,

    // Material
    MaterialModule,
  ],
  exports: [
    // Angular
    CommonModule,
    RouterModule,

    // Material
    MaterialModule,

    // Shared Components
    ForbiddenComponent,
    NotFoundComponent,

  ],
})
export class SharedModule {}
