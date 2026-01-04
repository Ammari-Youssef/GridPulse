import { DeviceStatus } from "@models/enums/device-status.enum";

export interface Device {
  id: string;
  name: string;
  model: string;
  serialNumber: string;
  manufacturer: string;
  softwareVersion: string;
  ip: string;
  status: DeviceStatus;
  lastSeen: string;
  gpsLat: number;
  gpsLong: number;
  swUpdateTime: string | null;
  fleet: { id: string; name: string };
  user: { id: string; firstname: string; lastname: string };
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
  source: string;
}