import { DeviceStatus } from "@models/enums/device-status.enum";

export interface DeviceLocation {
  id: string;
  name: string;
  status: DeviceStatus
  gpsLat: number | null;
  gpsLong: number | null;
  lastSeen: string;
}

export interface GetDeviceLocationsResponse {
  getAllDevices: DeviceLocation[];
}