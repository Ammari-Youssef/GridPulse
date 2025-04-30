package com.youssef.GridPulse.configuration;

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

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        // Extract the JWT token from the Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // If no token is present, continue the filter chain to next filter
            return; // Exit the method
        }

        jwt = authHeader.substring(7); // Extract the token by removing "Bearer " prefix

        userEmail = jwtService.extractUsername(jwt);

        // when username is there but not authenticated.
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            // if token is valid, set the authentication in the security context and update it
            if(jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // we don't have credentials.
                        userDetails.getAuthorities()
                ); // Create a new authentication token with the user details and authorities
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //

                // set/update the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); // Continue the filter chain to the next filter. Never forget this line.
    }

}
