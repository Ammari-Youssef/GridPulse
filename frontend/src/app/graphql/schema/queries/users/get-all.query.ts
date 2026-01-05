import { gql } from 'apollo-angular';

export const GET_ALL_USERS = gql`
  query GetAllUsers {
    getAllUsers {
      id
      firstname
      lastname
      email
      role
      enabled
      createdAt
    }
  }
`;
