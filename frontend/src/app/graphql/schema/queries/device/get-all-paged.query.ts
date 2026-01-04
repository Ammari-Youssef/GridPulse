import { gql } from 'apollo-angular';

export const GET_ALL_DEVICE_PAGED = gql`
  query GetAllDevicesPaged($pageRequestInput: PageRequestInput!) {
    getAllDevicePaged(pageRequest: $pageRequestInput) {
      pageNumber
      pageSize
      totalElements
      totalPages
      last
      content {
        id
        name
        serialNumber
        gpsLat
        gpsLong
        lastSeen
        status
        model
        softwareVersion
        manufacturer
        swUpdateTime
        ip
        createdBy
        updatedBy
        createdAt
        updatedAt
        source
        fleet {
          id
          name
          location
          owner
          description
          createdBy
          updatedBy
          createdAt
          updatedAt
          source
        }
        inverter {
          id
          name
          model
          version
          manufacturer
          createdBy
          updatedBy
          createdAt
          updatedAt
          source
        }
        meter {
          id
          name
          model
          manufacturer
          version
          createdBy
          updatedBy
          createdAt
          updatedAt
          source
        }
        bms {
          id
          name
          model
          manufacturer
          version
          soh
          soc
          batteryChemistry
          createdBy
          updatedBy
          createdAt
          updatedAt
          source
        }
        user {
          id
          email
          firstname
          lastname
          password
          role
          enabled
          createdBy
          updatedBy
          createdAt
          updatedAt
          source
        }
        operator {
          id
          email
          firstname
          lastname
          password
          role
          enabled
          createdBy
          updatedBy
          createdAt
          updatedAt
          source
        }
      }
    }
  }
`;
