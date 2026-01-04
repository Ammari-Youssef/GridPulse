import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '@shared/ui/material/material.module';
import { FleetListComponent } from '@features/fleets/fleet-list/fleet-list.component';
import { DashboardModule } from './dashboard/dashboard.module';
import { DevicesModule } from './devices/devices.module';

@NgModule({
  declarations: [FleetListComponent],
  imports: [CommonModule, MaterialModule, DashboardModule, DevicesModule],
  exports: [FleetListComponent],
})
export class FeaturesModule {}
