import { gql } from 'apollo-angular';

export const GET_ALL_FLEET_PAGED = gql`
  query GetAllFleetPaged($pageRequestInput: PageRequestInput!) {
    getAllFleetPaged(pageRequest: $pageRequestInput) {
      pageNumber
      pageSize
      totalElements
      totalPages
      last
      content {
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
    }
  }
`;
