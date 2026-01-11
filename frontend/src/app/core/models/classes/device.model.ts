import { DeviceStatus } from '@models/enums/device-status.enum';
import { BMS } from './bms.model';
import { Meter } from './meter.model';
import { User } from './User.model';
import { Fleet } from './fleet.model';

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

  // Relationships
  fleet: Fleet;
  user: User;
  bms: BMS;
  meter: Meter;

  // Auditing
  createdAt: string;
  updatedAt: string;
  createdBy: string;
  updatedBy: string;
  source: string;
}
