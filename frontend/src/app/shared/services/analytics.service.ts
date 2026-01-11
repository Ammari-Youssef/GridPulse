import { Injectable } from '@angular/core';
import { Apollo } from 'apollo-angular';
import { Observable, map, catchError, throwError } from 'rxjs';

import {
  DeviceAnalyticsData,
  ChartConfig,
  DeviceListItem,
} from '@core/models/interfaces/analytics/device-analytics.interface';
import { GET_ALL_DEVICES_SIMPLE } from '@graphql/schema/queries/device/get-all-simple.query';
import { GetAllDevicesSimpleResponse } from '@core/models/interfaces/analytics/analytics.response';
import { BatteryHealthStatus } from '@core/models/enums/bms/battery-health-status.enum';

@Injectable({
  providedIn: 'root',
})
export class AnalyticsService {
  constructor(private readonly apollo: Apollo) {}

  getAllDevices(): Observable<DeviceListItem[]> {
    return this.apollo
      .query<GetAllDevicesSimpleResponse>({
        query: GET_ALL_DEVICES_SIMPLE,
        fetchPolicy: 'network-only',
      })
      .pipe(
        map((result) =>
          result.data!.getAllDevices.map((device) => ({
            id: device.id,
            name: device.name,
            serialNumber: device.serialNumber,
            model: device.model,
            status: device.status,
          }))
        ),
        catchError((error) => {
          console.error('Error fetching devices:', error);
          return throwError(() => error);
        })
      );
  }

  getDeviceAnalytics(deviceId: string): Observable<DeviceAnalyticsData> {
    return this.apollo
      .query<GetAllDevicesSimpleResponse>({
        query: GET_ALL_DEVICES_SIMPLE,
        fetchPolicy: 'network-only',
      })
      .pipe(
        map((result) => {
          const device = result.data!.getAllDevices.find(
            (d) => d.id === deviceId
          );
          if (!device) {
            throw new Error('Device not found');
          }
          return device;
        }),
        catchError((error) => {
          console.error('Error fetching device analytics:', error);
          return throwError(() => error);
        })
      );
  }

  // Create simple gauge/snapshot charts from current data
  prepareBatteryGaugeData(device: DeviceAnalyticsData): ChartConfig {
    return {
      labels: ['SOC (%)', 'Temperature (°C)', 'Voltage (V)'],
      datasets: [
        {
          label: 'Current Values',
          data: [device.bms.soc, device.bms.temperature, device.bms.voltage],
          backgroundColor: [
            'rgba(76, 175, 80, 0.8)',
            'rgba(255, 152, 0, 0.8)',
            'rgba(33, 150, 243, 0.8)',
          ],
          borderColor: ['#4CAF50', '#FF9800', '#2196F3'],
          borderWidth: 2,
        },
      ],
    };
  }

  prepareEnergyComparisonData(device: DeviceAnalyticsData): ChartConfig {
    return {
      labels: ['Energy Consumed', 'Energy Produced'],
      datasets: [
        {
          label: 'Energy (kWh)',
          data: [device.meter.energyConsumed, device.meter.energyProduced],
          backgroundColor: ['rgba(244, 67, 54, 0.8)', 'rgba(76, 175, 80, 0.8)'],
          borderColor: ['#f44336', '#4CAF50'],
          borderWidth: 2,
        },
      ],
    };
  }

  prepareBatteryHealthData(device: DeviceAnalyticsData): ChartConfig {
    const sohValue = this.mapHealthToNumber(device.bms.soh);
    const remaining = 100 - sohValue;

    return {
      labels: ['Current Health', 'Degradation'],
      datasets: [
        {
          label: 'Battery Health (%)',
          data: [sohValue, remaining],
          backgroundColor: [
            'rgba(76, 175, 80, 0.8)',
            'rgba(189, 189, 189, 0.3)',
          ],
          borderColor: ['#4CAF50', '#BDBDBD'],
          borderWidth: 2,
        },
      ],
    };
  }

  preparePowerOverviewData(device: DeviceAnalyticsData): ChartConfig {
    return {
      labels: ['Current Power Dispatch'],
      datasets: [
        {
          label: 'Power (kW)',
          data: [device.meter.powerDispatched],
          backgroundColor: 'rgba(156, 39, 176, 0.8)',
          borderColor: '#9C27B0',
          borderWidth: 2,
        },
      ],
    };
  }

  private mapHealthToNumber(health: BatteryHealthStatus): number {
    const healthMap: Record<string, number> = {
      CRITICAL: 20,
      DEGRADED: 40,
      UNHEALTHY: 60,
      HEALTHY: 95,
      UNKNOWN: 50,
    };
    return healthMap[health];
  }
}

