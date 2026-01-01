import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

// Components
import { NavbarComponent } from '@layout/navbar/navbar.component';
import { ShellComponent } from '@layout/shell/shell.component';
import { SidebarComponent } from '@layout/sidebar/sidebar.component';

// Angular Material
import { MaterialModule } from '@shared/ui/material/material.module';

@NgModule({
  declarations: [NavbarComponent, ShellComponent, SidebarComponent],
  exports: [ShellComponent, NavbarComponent, SidebarComponent],
  imports: [
    CommonModule,
    RouterModule,

    // Angular Material Module
    MaterialModule,
  ],
})
export class LayoutModule {}
