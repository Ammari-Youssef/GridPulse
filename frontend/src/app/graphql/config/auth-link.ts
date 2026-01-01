import { ApolloLink } from '@apollo/client/core';

export const authLink = new ApolloLink((operation, forward) => {
  const token = localStorage.getItem('access_token');

  operation.setContext(
    ({ headers }: { headers?: Record<string, string> } = {}) => ({
      headers: {
        ...(headers ?? {}),
        // If no token, do not set Authorization or set to empty string
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
      },
    })
  );

  return forward(operation);
});
