package com.youssef.GridPulse.configuration.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class AuthenticationCustomEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String body = """
                {
                  "errors": [
                   {
                      "message": "Unauthorized: authentication required",
                      "extensions": {
                        "code": "UNAUTHORIZED",
                        "classification": "SECURITY"
                      }
                   }
                  ]
                }
                """;

        try (PrintWriter out = response.getWriter()) {
            out.write(body);
        }

        // Optional: log unauthorized access
        log.warn("Unauthorized access attempt to {}", request.getRequestURI());
    }

}

