package com.pm.fsnotes.service;



import com.pm.fsnotes.entity.Creator;
import com.pm.fsnotes.repository.CreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final CreatorRepository creatorRepository;

    public UserDetailsServiceImpl(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    /*
     * Load user by username from DB for Spring Security authentication.
     *
     * @param username provided during login
     * @return UserDetails object (Spring Security standard)
     * @throws UsernameNotFoundException if user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch Creator entity from DB
        Creator creator = creatorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Return Spring Security's UserDetails object
        return new User(
                creator.getUsername(),
                creator.getPassword(),
                Collections.emptyList() // No roles for now (can add roles later)
        );
    }
}

