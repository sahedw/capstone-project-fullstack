package com.github.sahedw.backend.security;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.github.sahedw.backend.exceptions.UsernameAlreadyExistsException;
import com.github.sahedw.backend.models.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodSpotUserServiceTest {

    FoodSpotUserRepo foodSpotUserRepoMocked = mock(FoodSpotUserRepo.class);

    IdService idServiceMocked = mock(IdService.class);

    CloudinaryService cloudinaryService = mock(CloudinaryService.class);

    FoodSpotUserService foodSpotUserServiceMocked = new FoodSpotUserService(foodSpotUserRepoMocked, idServiceMocked, cloudinaryService);

    SecurityContext securityContext = mock(SecurityContext.class);

    Cloudinary cloudinary = new Cloudinary();

    @Mock
    Authentication authentication = mock(Authentication.class);

    CloudinaryService cloudinaryServiceReal = new CloudinaryService(new Cloudinary());

    IdService idServiceReal = new IdService();
    FoodSpotUserService foodSpotUserServiceReal = new FoodSpotUserService(foodSpotUserRepoMocked, idServiceReal, cloudinaryServiceReal);

    PasswordEncoder encoder = mock(AbstractPasswordEncoder.class);


    @Test
    void expectNewUser_whenSignUp() {
        //GIVEN
        FoodSpotUserForSignUp incomingUser = new FoodSpotUserForSignUp( "hans", "hans1", "Hamburg", "");
        String expected = incomingUser.username();
        //WHEN
        when(idServiceMocked.randomId()).thenReturn("123");
        when(encoder.encode("hans1")).thenReturn("123456789");
        FoodSpotUser newUser = new FoodSpotUser(idServiceMocked.randomId(), incomingUser.username(), encoder.encode(incomingUser.password()), "Hamburg", List.of(), List.of(),  "");
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
        FoodSpotUser newUser = new FoodSpotUser("123" ,"franz", "franz1","Hamburg", List.of(), List.of(),"");
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
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), List.of(),"");
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
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), List.of(),"");

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
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), List.of(), expected);

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
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), List.of(), expected);

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
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), List.of(), expected);

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
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), List.of(), expected);

        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.empty());
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(NoSuchElementException.class, () -> foodSpotUserServiceReal.getUserSeed());
        verify(securityContext).getAuthentication();
        verify(authentication).getName();
    }

    @Test
    void expectAllCategoriesOfUser_whenGetCategoriesIsCalled() {
        Category category = new Category("1", "BURGER", new ImageDetails(new CategoryCSSDetails(0, 0, 0, "test"), new FoodSpotCSSDetails(0, "test")));
        List<Category> expected = new ArrayList<>(List.of(category));
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), expected, "");

        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<Category> actual = foodSpotUserServiceReal.getUserCategories();
        verify(securityContext).getAuthentication();
        verify(authentication).getName();

        assertEquals(expected, actual);
    }

    @Test
    void expectAddedCategoriesToUser_whenAddCategoryIsCalled() throws IOException {

        Category category1 = new Category("1", "BURGER", new ImageDetails(new CategoryCSSDetails(0, 0, 0, "test1"), new FoodSpotCSSDetails(0, "test1.de")));
        Category category2 = new Category("2", "PIZZA", new ImageDetails(new CategoryCSSDetails(0, 0, 0, "test2"), new FoodSpotCSSDetails(0, "test2.de")));
        List<Category> expected = new ArrayList<>();
        expected.add(category1);
        expected.add(category2);

        List<Category> beforeUpdate = new ArrayList<>();
        beforeUpdate.add(category1);


        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), beforeUpdate, "");
        FoodSpotUser updatedUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), expected, "");

        MockMultipartFile file1 = new MockMultipartFile("bgImage", "test1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("NormalImage", "test2".getBytes());

        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(cloudinaryService.uploadImage(file1)).thenReturn("test1.de");
        when(cloudinaryService.uploadImage(file2)).thenReturn("test2.de");

        List<Category> actual = foodSpotUserServiceMocked.addUserCategories(category2, file1, file2);
        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        assertEquals(expected, actual);
    }

    @Test
    void expectNoSuchElementException_whenAddCategoryIsCalled() throws IOException {
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", "Hamburg",new ArrayList<>(List.of()), List.of(), "abcdefg");
        Category category = new Category("2", "BURGER", new ImageDetails(new CategoryCSSDetails(0, 0, 0, "test1"), new FoodSpotCSSDetails(0, "test1.de")));
        MockMultipartFile file1 = new MockMultipartFile("bgImage", "test1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("NormalImage", "test2".getBytes());

        when(foodSpotUserRepoMocked.findByUsername("sahed")).thenReturn(Optional.empty());
        when(foodSpotUserRepoMocked.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(cloudinaryService.uploadImage(file1)).thenReturn("test1.de");
        when(cloudinaryService.uploadImage(file2)).thenReturn("test2.de");

        assertThrows(NoSuchElementException.class, () -> foodSpotUserServiceMocked.addUserCategories(category, file1, file2));
        verify(securityContext).getAuthentication();
        verify(authentication).getName();
    }
}
