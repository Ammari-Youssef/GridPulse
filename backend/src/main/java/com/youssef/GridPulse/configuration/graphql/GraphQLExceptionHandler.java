package com.youssef.GridPulse.configuration.graphql;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected List<GraphQLError> resolveToMultipleErrors(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof ConstraintViolationException cve) {
            log.error("Validation error for executionId: {}", env.getExecutionId(), ex);

            return cve.getConstraintViolations().stream()
                    .map(violation -> GraphqlErrorBuilder.newError(env)
                            .message(violation.getPropertyPath() + ": " + violation.getMessage())
                            .extensions(createErrorExtensions(ex, env))
                            .build())
                    .collect(Collectors.toList());
        }
        return super.resolveToMultipleErrors(ex, env);
    }

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        log.error("GraphQL error for executionId: {}", env.getExecutionId(), ex);

        return GraphqlErrorBuilder.newError(env)
                .message(getUserFriendlyMessage(ex))
                .extensions(createErrorExtensions(ex, env))
                .build();
    }

    private String getUserFriendlyMessage(Throwable ex) {
        if (ex instanceof ConstraintViolationException) return "Validation failed";
        if (ex instanceof EntityNotFoundException) return "Resource not found";
        if (ex instanceof AuthenticationException) return "Unauthorized: authentication required"; // 401
        if (ex instanceof AccessDeniedException) return "Forbidden: admin privileges required"; // 403
        if (ex instanceof IllegalArgumentException) return "Bad request";
        if (ex instanceof StaleObjectStateException || ex instanceof OptimisticLockException)
            return "Conflict occurred while updating the resource. Please retry.";
        if (ex instanceof DataIntegrityViolationException)
            return "Data integrity violation. Please check your input.";
        if (ex instanceof EmptyResultDataAccessException)
            return "Cannot delete: resource does not exist.";
        if (ex instanceof InvalidDataAccessApiUsageException)
            return "Invalid operation. Please check your request.";
        if (ex instanceof DataAccessException)
            return "Database access error. Please try again later.";

        return "An unexpected error occurred";
    }

    private Map<String, Object> createErrorExtensions(Throwable ex, DataFetchingEnvironment env) {
        Map<String, Object> extensions = new HashMap<>();
        extensions.put("code", getErrorCode(ex));
        extensions.put("executionId", env.getExecutionId());
        extensions.put("operation", env.getOperationDefinition().getName());
        extensions.put("reason", ex.getMessage());

        if (ex instanceof ConstraintViolationException) {
            extensions.put("details", getValidationDetails((ConstraintViolationException) ex));
        } else if (ex instanceof EntityNotFoundException) {
            extensions.put("resource", ex.getMessage());
        }

        return extensions;
    }

    private String getErrorCode(Throwable ex) {
        if (ex instanceof ConstraintViolationException) return "VALIDATION_ERROR";
        if (ex instanceof EntityNotFoundException) return "NOT_FOUND";
        if (ex instanceof AccessDeniedException) return "UNAUTHORIZED";
        if (ex instanceof IllegalArgumentException) return "BAD_REQUEST";
        if (ex instanceof StaleObjectStateException || ex instanceof OptimisticLockException) return "CONFLICT";
        if (ex instanceof DataIntegrityViolationException) return "DATA_INTEGRITY_CONFLICT";
        if (ex instanceof EmptyResultDataAccessException) return "DELETE_FAILED";
        if (ex instanceof InvalidDataAccessApiUsageException) return "INVALID_OPERATION";
        return "INTERNAL_ERROR";
    }

    private List<String> getValidationDetails(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toList());
    }
}
