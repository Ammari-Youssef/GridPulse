import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { MapComponent } from './map/map.component';
import { StatsCardComponent } from './stats-card/stats-card.component';

// Shared Modules
import { MaterialModule } from '@shared/ui/material/material.module';

@NgModule({
  declarations: [
    DashboardComponent,
    AnalyticsComponent,
    MapComponent,
    StatsCardComponent,
  ],
  imports: [CommonModule, RouterModule, MaterialModule],
  exports: [DashboardComponent, AnalyticsComponent],
})
export class DashboardModule {}
