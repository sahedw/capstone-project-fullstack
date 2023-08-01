package com.github.sahedw.backend.security;

import com.github.sahedw.backend.exceptions.UsernameAlreadyExistsException;
import com.github.sahedw.backend.models.FoodSpot;
import com.github.sahedw.backend.models.IdService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodSpotUserServiceTest {

    FoodSpotUserRepo foodSpotUserRepoMocked = mock(FoodSpotUserRepo.class);

    IdService idServiceMocked = mock(IdService.class);

    FoodSpotUserService foodSpotUserServiceMocked = new FoodSpotUserService(foodSpotUserRepoMocked, idServiceMocked);


    IdService idServiceReal = new IdService();
    FoodSpotUserService foodSpotUserServiceReal = new FoodSpotUserService(foodSpotUserRepoMocked, idServiceReal);

    PasswordEncoder encoder = mock(AbstractPasswordEncoder.class);


    @Test
    void expectNewUser_whenSignUp() {
        //GIVEN
        FoodSpotUserForSignUp incomingUser = new FoodSpotUserForSignUp( "hans", "hans1");
        String expected = incomingUser.username();
        //WHEN
        when(idServiceMocked.randomId()).thenReturn("123");
        when(encoder.encode("hans1")).thenReturn("123456789");
        FoodSpotUser newUser = new FoodSpotUser(idServiceMocked.randomId(), incomingUser.username(), encoder.encode(incomingUser.password()), List.of());
        when(foodSpotUserRepoMocked.insert(ArgumentMatchers.any(FoodSpotUser.class))).thenReturn(null);

        String actual = foodSpotUserServiceMocked.signUp(incomingUser);

        // THEN
        verify(idServiceMocked, times(2)).randomId();
        verify(encoder).encode("hans1");
        verify(foodSpotUserRepoMocked).insert(ArgumentMatchers.any(FoodSpotUser.class));
        assertEquals(expected, actual);
    }

    @Test
    void expectUsernameAlreadyExistsException_whenUsernameAlreadyExists() {
        // GIVEN
        FoodSpotUser newUser = new FoodSpotUser("123" ,"franz", "franz1", List.of());
        FoodSpotUserForSignUp newUserSignUp = new FoodSpotUserForSignUp("franz", "franz1");
        foodSpotUserServiceReal.signUp(newUserSignUp);
        // WHEN
        when(foodSpotUserRepoMocked.findByUsername("franz")).thenReturn(Optional.of(newUser));
        FoodSpotUserForSignUp secondUser = new FoodSpotUserForSignUp("franz", "franz2");

        // Assert
        assertThrows(UsernameAlreadyExistsException.class, () -> foodSpotUserServiceReal.signUp(secondUser));
    }

}
