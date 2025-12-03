import { gql } from "apollo-angular";

export const HELLO_QUERY = gql`
  query Hello {
    hello
    version
    uptime
  }
`;
