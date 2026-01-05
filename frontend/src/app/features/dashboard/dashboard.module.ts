import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';

import { DashboardComponent } from './dashboard/dashboard.component';
import { MapComponent } from './map/map.component';
import { StatsCardComponent } from './stats-card/stats-card.component';
import { MaterialModule } from '@shared/ui/material/material.module';

@NgModule({
  declarations: [
    DashboardComponent,
    MapComponent,
    StatsCardComponent,
  ],
  imports: [CommonModule, DashboardRoutingModule, MaterialModule],
})
export class DashboardModule {}
