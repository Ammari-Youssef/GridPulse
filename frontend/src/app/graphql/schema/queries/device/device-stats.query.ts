import { gql } from 'apollo-angular';

export const GET_DEVICE_STATS = gql`
  query GetDeviceStats {
    getDeviceStats {
      totalDevices
      onlineDevices
      offlineDevices
      maintenanceDevices
      totalAlerts
      criticalAlerts
    }
  }
`;
