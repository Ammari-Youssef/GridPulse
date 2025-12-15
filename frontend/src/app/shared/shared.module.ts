import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Angular Material Modules
import { MatButtonModule } from '@angular/material/button';
import { MatCardActions, MatCardModule } from '@angular/material/card';
import { ForbiddenComponent } from './pages/forbidden/forbidden.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [ForbiddenComponent, NotFoundComponent],
  exports: [ForbiddenComponent, NotFoundComponent],
  imports: [CommonModule, MatCardModule, MatCardActions, MatButtonModule, RouterModule],
})
export class SharedModule {}
