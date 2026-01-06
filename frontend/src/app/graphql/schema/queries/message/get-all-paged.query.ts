import { gql } from 'apollo-angular';

export const GET_ALL_MESSAGES_PAGED = gql`
  query GetAllMessagesPaged($pageRequestInput: PageRequestInput!) {
    getAllMessagePaged(pageRequest: $pageRequestInput) {
      pageNumber
      pageSize
      totalElements
      totalPages
      last
      content {
        id
        cloudMessageId
        messageText {
          __typename
          ... on InverterPayload {
            out_pwr
            voltage
            current
            frequency
            efficiency
            temperature
          }
          ... on MeterPayload {
            __typename
            pwrIn
            pwrOut
          }
          ... on BmsPayload {
            __typename
            batteryId
            batteryVolt
            batterySoc
            batteryCycle
            batterySoh
            batteryTemperature
          }
          ... on SystemPayload {
            __typename
            cpuUsage
            memoryUsage
            diskUsage
            temperature
            latitude
            longitude
          }
          ... on SoftwarePayload {
            __typename
            startTime
            endTime
            packageType
            version
            status
          }
          ... on HeartbeatPayload {
            __typename
            uptime
          }
          ... on IdsPayload {
            __typename
            attackType
            sourceIp
            destinationIp
            sourcePort
            destinationPort
            protocol
            rawMessage
          }
        }
        messageType
        status
        severity
        format
        priority
        explanation
        sentAt
        receivedAt
        createdBy
        updatedBy
        createdAt
        updatedAt
        source
        device {
          id
          name
          model
          serialNumber
          manufacturer
          softwareVersion
          ip
          swUpdateTime
          gpsLat
          gpsLong
          lastSeen
          status
          createdBy
          updatedBy
          createdAt
          updatedAt
          source
          fleet {
            id
            name
          }
        }
      }
    }
  }
`;
