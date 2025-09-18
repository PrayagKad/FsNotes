package com.pm.fsnotes.controller;

import com.pm.fsnotes.entity.Creator;
import com.pm.fsnotes.repository.CreatorRepository;
import com.pm.fsnotes.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private CreatorRepository creatorRepository;

    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public AuthController(CreatorRepository creatorRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.creatorRepository = creatorRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }



    /**
     * Register a new user (Creator).
     * - Hash the password using BCrypt.
     * - Save user in database.
     * - Return success message.
     *
     * @param creator request body containing username & password
     * @return message indicating registration status
     */
    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Creator creator) {
        // Hash the password before saving
        creator.setPassword(passwordEncoder.encode(creator.getPassword()));

        // Save the user in DB
        creatorRepository.save(creator);

        // Response
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return response;
    }

    /**
     * Authenticate user and return JWT token.
     * - Validate username and password using AuthenticationManager.
     * - If valid, generate JWT token.
     *
     * @param authRequest JSON with username & password
     * @return JSON with JWT token
     */
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Creator authRequest) {
        // Authenticate the user using Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        // If authentication is successful, generate JWT token
        String token = jwtUtil.generateToken(authRequest.getUsername());

        // Return token in response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", authRequest.getUsername());
        return response;
    }
}