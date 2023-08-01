package com.github.sahedw.backend.security;

import com.github.sahedw.backend.models.IdService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodSpotUserServiceTest {

    FoodSpotUserRepo foodSpotUserRepo = mock(FoodSpotUserRepo.class);

    IdService idService = mock(IdService.class);

    FoodSpotUserService foodSpotUserService = new FoodSpotUserService(foodSpotUserRepo, idService);

    PasswordEncoder encoder = mock(AbstractPasswordEncoder.class);


    @Test
    void expectNewUser_whenSignUp() {
        //GIVEN
        FoodSpotUserForSignUp incomingUser = new FoodSpotUserForSignUp( "hans", "hans1");
        String expected = incomingUser.username();
        //WHEN
        when(idService.randomId()).thenReturn("123");
        when(encoder.encode("hans1")).thenReturn("123456789");
        FoodSpotUser newUser = new FoodSpotUser(idService.randomId(), incomingUser.username(), encoder.encode(incomingUser.password()), List.of());
        when(foodSpotUserRepo.insert(ArgumentMatchers.any(FoodSpotUser.class))).thenReturn(null);

        String actual = foodSpotUserService.signUp(incomingUser);

        // THEN
        verify(idService, times(2)).randomId();
        verify(encoder).encode("hans1");
        verify(foodSpotUserRepo).insert(ArgumentMatchers.any(FoodSpotUser.class));
        assertEquals(expected, actual);
    }

}