import { Component, Input } from '@angular/core';
import { DeviceAnalyticsData } from '@core/models/interfaces/analytics/device-analytics.interface';

@Component({
  selector: 'app-stats-overview',
  standalone: false,
  templateUrl: './stats-overview.component.html',
  styleUrl: './stats-overview.component.scss',
})
export class StatsOverviewComponent {
  @Input() deviceData!: DeviceAnalyticsData;

  get energyEfficiency(): number {
    if (!this.deviceData?.meter) return 0;
    const { energyProduced, energyConsumed } = this.deviceData.meter;
    if (energyConsumed === 0) return 0;
    return (energyProduced / energyConsumed) * 100;
  }

  get batteryStatus(): string {
    if (!this.deviceData?.bms) return 'Unknown';
    const soc = this.deviceData.bms.soc;
    if (soc >= 80) return 'Excellent';
    if (soc >= 50) return 'Good';
    if (soc >= 20) return 'Low';
    return 'Critical';
  }

  get batteryStatusColor(): string {
    if (!this.deviceData?.bms) return 'gray';
    const soc = this.deviceData.bms.soc;
    if (soc >= 80) return '#4CAF50';
    if (soc >= 50) return '#8BC34A';
    if (soc >= 20) return '#FF9800';
    return '#f44336';
  }
}