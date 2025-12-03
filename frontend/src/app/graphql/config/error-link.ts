import { CombinedGraphQLErrors } from '@apollo/client';
import { ErrorLink } from '@apollo/client/link/error';

export const errorLink = new ErrorLink((options: ErrorLink.ErrorHandlerOptions) => {
  const { error, result, operation } = options;

  // Check if this is a GraphQL error
  if (CombinedGraphQLErrors.is(error)) {
    if (error.errors.length) {
      error.errors.forEach(err => {
        console.error(`[GraphQL error]: Message: ${err.message}`, {
          path: err.path,
          extensions: err.extensions,
          operationName: operation.operationName,
        });
      });
    }

    if (error) {
      console.error(`[Network error]:`, error.name, error.message);
    }
  } else {
    // Fallback for other types of errors
    console.error('[Apollo Error]', error, { operation, result });
  }
});