import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

// Components
import { NavbarComponent } from '@layout/navbar/navbar.component';
import { ShellComponent } from '@layout/shell/shell.component';
import { SidebarComponent } from '@layout/sidebar/sidebar.component';

// Angular Material
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { MatListModule } from '@angular/material/list';


@NgModule({
  declarations: [NavbarComponent, ShellComponent, SidebarComponent],
  exports: [ShellComponent, NavbarComponent, SidebarComponent],
  imports: [
    CommonModule,
    RouterModule,

    // Angular Material Modules
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatSidenavModule,

    MatListModule,
    MatIconModule,
    RouterModule,
  ],
})
export class LayoutModule {}
