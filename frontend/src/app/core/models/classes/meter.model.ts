export interface Meter {
  id: string;
  name: string;
  model: string;
  manufacturer: string;
  version: string;

  powerDispatched: number;
  energyConsumed: number;
  energyProduced: number;

  createdBy: string;
  updatedBy: string;
  createdAt: Date;
  updatedAt: Date;
  source: string;
}