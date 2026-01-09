import { Component, OnInit, OnDestroy } from '@angular/core';
import { ChartConfig, DeviceAnalyticsData, DeviceListItem } from '@core/models/interfaces/analytics/device-analytics.interface';
import { AnalyticsService } from '@shared/services/analytics.service';
import { SnackbarService } from '@shared/services/snackbar.service';
import { Subject, takeUntil } from 'rxjs';


@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.scss'],
  standalone: false,
})
export class AnalyticsComponent implements OnInit, OnDestroy {
  private readonly destroy$ = new Subject<void>();

  devices: DeviceListItem[] = [];
  selectedDeviceId: string | null = null;
  deviceData: DeviceAnalyticsData | null = null;

  batteryGaugeData: ChartConfig | null = null;
  energyComparisonData: ChartConfig | null = null;
  batteryHealthData: ChartConfig | null = null;
  powerOverviewData: ChartConfig | null = null;

  loadingDevices = false;
  loadingAnalytics = false;

  constructor(
    private readonly analyticsService: AnalyticsService,
    private readonly snackbarService: SnackbarService
  ) {}

  ngOnInit(): void {
    this.loadDevices();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  loadDevices(): void {
    this.loadingDevices = true;

    this.analyticsService
      .getAllDevices()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (devices: DeviceListItem[]) => {
          this.devices = devices;
          this.loadingDevices = false;

          // Auto-select first device if available
          if (devices.length > 0 && !this.selectedDeviceId) {
            this.selectedDeviceId = devices[0].id;
            this.loadAnalytics();
          }
        },
        error: (error: unknown) => {
          this.snackbarService.showError('Failed to load devices ' + error);
          this.loadingDevices = false;
        },
      });
  }

  onDeviceChange(): void {
    if (this.selectedDeviceId) {
      this.loadAnalytics();
    }
  }

  loadAnalytics(): void {
    if (!this.selectedDeviceId) return;

    this.loadingAnalytics = true;

    this.analyticsService
      .getDeviceAnalytics(this.selectedDeviceId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data: DeviceAnalyticsData) => {
          this.deviceData = data;

          // Prepare charts from current data
          this.batteryGaugeData =
            this.analyticsService.prepareBatteryGaugeData(data);
          this.energyComparisonData =
            this.analyticsService.prepareEnergyComparisonData(data);
          this.batteryHealthData =
            this.analyticsService.prepareBatteryHealthData(data);
          this.powerOverviewData =
            this.analyticsService.preparePowerOverviewData(data);

          this.loadingAnalytics = false;
        },
        error: (error: unknown) => {
          this.snackbarService.showError('Failed to load device analytics ' + error);
          this.loadingAnalytics = false;
        },
      });
  }

  refreshData(): void {
    if (this.selectedDeviceId) {
      this.loadAnalytics();
    } else {
      this.snackbarService.showInfo('Please select a device first');
    }
  }

  getSelectedDeviceName(): string {
    if (!this.selectedDeviceId || !this.devices.length) return '';
    const device = this.devices.find((d) => d.id === this.selectedDeviceId);
    return device ? `${device.name} - ${device.serialNumber}` : '';
  }
}
