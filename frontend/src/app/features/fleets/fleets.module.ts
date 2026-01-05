import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FleetListComponent } from './fleet-list/fleet-list.component';
import { FleetsRoutingModule } from './fleets-routing.module';

@NgModule({
  declarations: [FleetListComponent],
  imports: [CommonModule, FleetsRoutingModule],
})
export class FleetsModule {}
