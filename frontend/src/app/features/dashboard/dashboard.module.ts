import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { MapComponent } from './map/map.component';
import { StatsCardComponent } from './stats-card/stats-card.component';

@NgModule({
  declarations: [
    DashboardComponent,
    AnalyticsComponent,
    MapComponent,
    StatsCardComponent,
  ],
  imports: [CommonModule, RouterModule],
  exports: [DashboardComponent, AnalyticsComponent],
})
export class DashboardModule {}
