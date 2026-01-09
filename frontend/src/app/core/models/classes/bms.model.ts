import { BatteryChemistry } from '@models/enums/bms/battery-chemistry.enum';
import { BatteryHealthStatus } from '@models/enums/bms/battery-health-status.enum';

export interface BMS {
  id: string;
  name: string;
  model: string;
  manufacturer: string;
  version: string;

  soh: BatteryHealthStatus;
  soc: number;
  batteryChemistry: BatteryChemistry;
  cycles: number;
  temperature: number;
  voltage: number;

  createdBy: string;
  updatedBy: string;
  createdAt: Date;
  updatedAt: Date;
  source: string;
}
