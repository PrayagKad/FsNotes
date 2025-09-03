package com.pm.fsnotes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * This filter checks every incoming HTTP request for a JWT token in the Authorization header.
 * If a valid token is found, it sets the authentication in the security context.
 */
@Component
public class jwtFilter extends OncePerRequestFilter {

    @Autowired
    private jwtUtil jwtUtil;

    @Autowired
    private com.pm.fsnotes.CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // Check if the header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7); // Extract the token
            try {
                username = jwtUtil.extractUsername(jwt); // Get username from token
            } catch (Exception e) {
                // If token is invalid, username will remain null
            }
        }

        // If we have a username and no authentication is set yet
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Validate the token
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                // Create authentication token and set it in the context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
