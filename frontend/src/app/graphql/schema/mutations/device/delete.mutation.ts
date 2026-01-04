import { gql } from 'apollo-angular';

export const DELETE_DEVICE_MUTATION = gql`
  mutation DeleteDevice($id: UUID!) {
    deleteDevice(id: $id)
  }
`;
