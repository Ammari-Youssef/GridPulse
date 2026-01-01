import { gql } from "apollo-angular";


export const LOGIN_MUTATION = gql`
    mutation Login($email: String!, $password: String!) {
    login(loginInput: { email: $email, password: $password }) {
        accessToken
        refreshToken
    }
}
`;