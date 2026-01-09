import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AnalyticsRoutingModule } from './analytics-routing.module';
import { BatteryChartComponent } from './charts/battery-chart/battery-chart.component';
import { EnergyChartComponent } from './charts/energy-chart/energy-chart.component';
import { StatsOverviewComponent } from './charts/stats-overview/stats-overview.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '@shared/shared.module';
import { AnalyticsComponent } from './analytics/analytics.component';


@NgModule({
  declarations: [
    AnalyticsComponent,
    BatteryChartComponent,
    EnergyChartComponent,
    StatsOverviewComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    AnalyticsRoutingModule
  ]
})
export class AnalyticsModule { }
