import { gql } from 'apollo-angular';

export const LOGOUT_MUTATION = gql`
  mutation Logout {
    logout
  }
`;
