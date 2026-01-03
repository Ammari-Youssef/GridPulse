import { gql } from 'apollo-angular';

export const DELETE_FLEET_MUTATION = gql`
  mutation DeleteFleet($id: UUID!) {
    deleteFleet(id: $id)
  }
`;
