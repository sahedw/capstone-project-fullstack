package com.github.sahedw.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class FoodSpotUserDetailsService implements UserDetailsService {

    private final FoodSpotUserRepo foodSpotUserRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FoodSpotUser foodSpotUser = foodSpotUserRepo.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Username: " + username + " not found!"));
        return new User(foodSpotUser.username(), foodSpotUser.password(), Collections.emptyList());
    }
}
