package com.pm.fsnotes.security;

import com.pm.fsnotes.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /*
     * Main security configuration for the application.
     * - Disables CSRF (since we're using JWT, not sessions)
     * - Allows certain endpoints without authentication (auth APIs, H2 console)
     * - Requires authentication for all other requests
     * - Adds custom JWT filter for token validation
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for APIs
                .csrf(csrf -> csrf.disable())

                // Disable frame options so H2 console works
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // Authorize HTTP requests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()        // Allow register/login endpoints
                        .requestMatchers("/notes/share/**").permitAll() // Allow shared notes without login
                        .requestMatchers("/h2-console/**").permitAll()  // Allow H2 database console
                        .anyRequest().authenticated()                   // All other endpoints require JWT auth
                )

                // Use stateless session since we are using JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Add JWT filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
      AuthenticationProvider using our custom UserDetailsService and password encoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Load users from DB
        provider.setPasswordEncoder(passwordEncoder());     // Encode passwords
        return provider;
    }

    /*
      BCryptPasswordEncoder for password hashing.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * AuthenticationManager bean to handle authentication logic during login.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
