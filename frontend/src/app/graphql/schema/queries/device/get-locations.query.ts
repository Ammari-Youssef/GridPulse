import { gql } from 'apollo-angular';

export const GET_DEVICE_LOCATIONS = gql`
  query GetDeviceLocations {
    getAllDevices {
      id
      name
      status
      gpsLat
      gpsLong
      lastSeen
    }
  }
`;