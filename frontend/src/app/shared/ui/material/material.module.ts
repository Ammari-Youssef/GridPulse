import { NgModule } from '@angular/core';

// Angular Material
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDividerModule } from '@angular/material/divider';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';


const MATERIAL_MODULES = [
  MatButtonModule,
  MatCardModule,
  MatIconModule,
  MatFormFieldModule,
  MatInputModule,
  MatToolbarModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatMenuModule,
  MatDividerModule,
  MatTooltipModule,

  MatToolbarModule,
  MatIconModule,
  MatButtonModule,
  MatTooltipModule,
  MatSidenavModule,
  MatListModule,
  MatToolbarModule
];

@NgModule({
  imports: [ ...MATERIAL_MODULES ],
  exports: [ ...MATERIAL_MODULES ],
})
export class MaterialModule {}
