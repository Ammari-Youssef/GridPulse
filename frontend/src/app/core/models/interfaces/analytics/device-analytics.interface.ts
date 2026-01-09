import { BatteryChemistry } from "@core/models/enums/bms/battery-chemistry.enum";
import { BatteryHealthStatus } from "@core/models/enums/bms/battery-health-status.enum";


export interface DeviceAnalyticsData {
  id: string;
  name: string;
  model: string;
  serialNumber: string;
  status: string;
  bms: BmsAnalytics;
  meter: MeterAnalytics;
}

export interface BmsAnalytics {
  id: string;
  soc: number;
  soh: BatteryHealthStatus;
  temperature: number;
  voltage: number;
  cycles: number;
  batteryChemistry: BatteryChemistry;
}

export interface MeterAnalytics {
  id: string;
  powerDispatched: number;
  energyConsumed: number;
  energyProduced: number;
}

export interface ChartDataset {
  label: string;
  data: number[];
  borderColor?: string[] | string;
  backgroundColor?: string[] | string;
  tension?: number;
  borderWidth?: number;
}

export interface ChartConfig {
  labels: string[];
  datasets: ChartDataset[];
}

export interface DeviceListItem {
  id: string;
  name: string;
  serialNumber: string;
  model: string;
  status: string;
}
