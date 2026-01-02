export interface DeviceStatsResponse {
  totalDevices: number;
  onlineDevices: number;
  offlineDevices: number;
  maintenanceDevices: number;
  totalAlerts: number;
  criticalAlerts: number;
}

export interface GetDeviceStatsResponse {
  getDeviceStats: DeviceStatsResponse;
}
