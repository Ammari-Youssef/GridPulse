import { Component, OnInit } from '@angular/core';
import {
  DeviceStatsResponse,
  GetDeviceStatsResponse,
} from '@core/models/interfaces/device-stats.response';
import { AuthService } from '@core/services/auth.service';
import { GET_DEVICE_STATS } from '@graphql/schema/queries/device/device-stats.query';
import { Apollo } from 'apollo-angular';
import { Observable, filter, map, of } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
  standalone: false,
})
export class DashboardComponent implements OnInit {
  readonly firstname$;
  metrics$: Observable<DeviceStatsResponse> = of({
    totalDevices: 0,
    onlineDevices: 0,
    offlineDevices: 0,
    maintenanceDevices: 0,
    totalAlerts: 0,
    criticalAlerts: 0,
  });

  constructor(
    private readonly authService: AuthService,
    private readonly apollo: Apollo
  ) {
    this.firstname$ = this.authService.user$.pipe(
      map((user) => user?.firstname)
    );
  }

  ngOnInit() {
    this.metrics$ = this.apollo
      .watchQuery<GetDeviceStatsResponse>({
        query: GET_DEVICE_STATS,
        pollInterval: 30000, // Auto-refresh every 30s
        fetchPolicy: 'network-only',
        errorPolicy: 'all',
      })
      .valueChanges.pipe(
        filter((result) => !!result.data?.getDeviceStats),
        map((result) => {
          return result.data!.getDeviceStats as DeviceStatsResponse;
        })
      );
  }
}
