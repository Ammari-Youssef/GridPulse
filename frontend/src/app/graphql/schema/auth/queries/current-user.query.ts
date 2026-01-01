import { gql } from 'apollo-angular';

export const CURRENT_USER_QUERY = gql`
  query GetCurrentUser {
    getCurrentUser {
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
`;
