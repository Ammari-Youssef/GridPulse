import { gql } from 'apollo-angular';

export const REFRESH_TOKEN_MUTATION = gql`
  mutation RefreshToken($token: String!) {
    refreshToken(refreshToken: $token) {
      __typename
      accessToken
      refreshToken
    }
  }
`;
