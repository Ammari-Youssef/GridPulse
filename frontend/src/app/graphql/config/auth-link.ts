import { ApolloLink } from '@apollo/client/core';

export const authLink = new ApolloLink((operation, forward) => {
  const token = localStorage.getItem('token');

  operation.setContext(({ headers }: { headers?: Record<string, string> }) => {
    return {
      headers: {
        ...(headers ?? {}),
        Authorization: token ? `Bearer ${token}` : '',
      },
    };
  });

  return forward(operation);
});
