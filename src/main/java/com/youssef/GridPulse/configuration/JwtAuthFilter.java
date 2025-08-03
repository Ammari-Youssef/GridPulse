package com.youssef.GridPulse.configuration;

import com.youssef.GridPulse.token.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 *
 This class is responsible for filtering JWT tokens from incoming requests.
 * It extends OncePerRequestFilter to ensure that the filter is executed once per request.
 * It is used to validate the JWT token and set the authentication in the security context.
 */

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Wrap request to allow body re-reading
        CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);

        // Read body only once
        String body = new String(cachedRequest.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // Skip JWT for login and register mutations
        if (request.getRequestURI().equals("/graphql") &&
                body.contains("mutation") &&
                (body.contains("login") || body.contains("register")) || body.contains("refreshToken")) {
            filterChain.doFilter(cachedRequest, response);
            return;
        }

        // Check Authorization header
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
            {
              "errors": [
                {
                  "message": "Unauthorized",
                  "extensions": { "classification": "UNAUTHORIZED" }
                }
              ]
            }
        """);
            return; // don't continue filter chain
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // Check if the token is valid and not expired or revoked
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if (jwtService.isTokenValid(jwt, userDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(cachedRequest, response);
    }


}
