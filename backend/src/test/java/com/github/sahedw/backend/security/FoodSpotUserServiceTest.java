package com.github.sahedw.backend.security;

import com.github.sahedw.backend.exceptions.UsernameAlreadyExistsException;
import com.github.sahedw.backend.models.FoodSpot;
import com.github.sahedw.backend.models.FoodSpotRepo;
import com.github.sahedw.backend.models.IdService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodSpotUserServiceTest {

    FoodSpotUserRepo foodSpotUserRepoMocked = mock(FoodSpotUserRepo.class);

    IdService idServiceMocked = mock(IdService.class);

    FoodSpotUserService foodSpotUserServiceMocked = new FoodSpotUserService(foodSpotUserRepoMocked, idServiceMocked);

    SecurityContext securityContext = mock(SecurityContext.class);

    @Mock
    Authentication authentication = mock(Authentication.class);


    IdService idServiceReal = new IdService();
    FoodSpotUserService foodSpotUserServiceReal = new FoodSpotUserService(foodSpotUserRepoMocked, idServiceReal);

    PasswordEncoder encoder = mock(AbstractPasswordEncoder.class);


    @Test
    void expectNewUser_whenSignUp() {
        //GIVEN
        FoodSpotUserForSignUp incomingUser = new FoodSpotUserForSignUp( "hans", "hans1", "Hamburg", "");
        String expected = incomingUser.username();
        //WHEN
        when(idServiceMocked.randomId()).thenReturn("123");
        when(encoder.encode("hans1")).thenReturn("123456789");
        FoodSpotUser newUser = new FoodSpotUser(idServiceMocked.randomId(), incomingUser.username(), encoder.encode(incomingUser.password()), "Hamburg", List.of(), "");
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
        FoodSpotUser newUser = new FoodSpotUser("123" ,"franz", "franz1","Hamburg", List.of(), "");
        FoodSpotUserForSignUp newUserSignUp = new FoodSpotUserForSignUp("franz", "franz1" , "Hamburg", "");
        foodSpotUserServiceReal.signUp(newUserSignUp);
        // WHEN
        when(foodSpotUserRepoMocked.findByUsername("franz")).thenReturn(Optional.of(newUser));
        FoodSpotUserForSignUp secondUser = new FoodSpotUserForSignUp("franz", "franz2" , "Hamburg", "");

        // Assert
        assertThrows(UsernameAlreadyExistsException.class, () -> foodSpotUserServiceReal.signUp(secondUser));
    }

    @Test
    void expectUserCity_whenGetUserCityIsCalled() {
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), "");
        String expected = "Hamburg";
        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String actual = foodSpotUserServiceReal.getUserCity();

        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        assertEquals(expected, actual);
    }

    @Test
    void expectNoSuchElementException_whenGetUserCityIsCalled() {
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), "");

        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.empty());
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(NoSuchElementException.class, () -> foodSpotUserServiceReal.getUserCity());
        verify(securityContext).getAuthentication();
        verify(authentication).getName();
    }

    @Test
    void expectSeed_whenSetUserSeedIsCalled() {
        String expected = "abcdefg";
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), expected);

        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        FoodSpotUserOnlyUsernameAndSeed userWithNewSeed = new FoodSpotUserOnlyUsernameAndSeed("sahed", expected);
        String actual = foodSpotUserServiceReal.setSeed(userWithNewSeed);

        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        assertEquals(expected, actual);
    }

    @Test
    void expectNoSuchElementException_whenSetUserSeedIsCalled() {
        String expected = "abcdefg";
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), expected);

        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.empty());
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        FoodSpotUserOnlyUsernameAndSeed userWithNewSeed = new FoodSpotUserOnlyUsernameAndSeed("sahed", expected);

        assertThrows(NoSuchElementException.class, () -> foodSpotUserServiceReal.setSeed(userWithNewSeed));
        verify(securityContext).getAuthentication();
        verify(authentication).getName();
    }

    @Test
    void expectSeed_whenGetUserSeedIsCalled() {
        String expected = "abcdefg";
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), expected);

        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        String actual = foodSpotUserServiceReal.getUserSeed();

        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        assertEquals(expected, actual);
    }

    @Test
    void expectNoSuchElementException_whenGetUserSeedIsCalled() {
        String expected = "abcdefg";
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), expected);

        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.empty());
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(NoSuchElementException.class, () -> foodSpotUserServiceReal.getUserSeed());
        verify(securityContext).getAuthentication();
        verify(authentication).getName();
    }
}
