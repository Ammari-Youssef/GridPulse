import { gql } from 'apollo-angular';

export const GET_ALL_DEVICES_SIMPLE = gql`
  query GetAllDevicesSimple {
    getAllDevices {
      id
      name
      serialNumber
      model
      status
      bms {
        id
        soc
        soh
        temperature
        voltage
        cycles
        batteryChemistry
      }
      meter {
        id
        powerDispatched
        energyConsumed
        energyProduced
      }
    }
  }
`;
