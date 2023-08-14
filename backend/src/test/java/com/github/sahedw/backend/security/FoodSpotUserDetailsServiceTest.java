package com.github.sahedw.backend.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodSpotUserDetailsServiceTest {
    FoodSpotUserRepo foodSpotUserRepo = mock(FoodSpotUserRepo.class);

    FoodSpotUserDetailsService foodSpotUserDetailsService = new FoodSpotUserDetailsService(foodSpotUserRepo);

    @Test
    void loadUserByUsername() {
        String givenUserName = "sahed";
        FoodSpotUser expectedUser = new FoodSpotUser("123", "sahed", "sahed1","Hamburg",List.of(), "");
        when(foodSpotUserRepo.findByUsername(givenUserName)).thenReturn(Optional.of(expectedUser));
        UserDetails actualUser = foodSpotUserDetailsService.loadUserByUsername(givenUserName);
        verify(foodSpotUserRepo).findByUsername(givenUserName);
        Assertions.assertEquals(givenUserName, actualUser.getUsername());
    }

    @Test
    void expectUsernameNotFoundException_whenFindByUsername() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> foodSpotUserDetailsService.loadUserByUsername("non-existent"));
    }

}

