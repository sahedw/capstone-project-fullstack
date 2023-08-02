package com.github.sahedw.backend.models;

import com.github.sahedw.backend.security.FoodSpotUser;
import com.github.sahedw.backend.security.FoodSpotUserRepo;
import com.github.sahedw.backend.security.FoodSpotUserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodSpotServiceTest {

    IdService idService = mock(IdService.class);
    FoodSpotRepo foodSpotRepo = mock(FoodSpotRepo.class);
    FoodSpotUserRepo foodSpotUserRepo = mock(FoodSpotUserRepo.class);
    FoodSpotService foodSpotService = new FoodSpotService(foodSpotRepo, idService, foodSpotUserRepo);

    SecurityContext securityContext = mock(SecurityContext.class);

    @Mock
    Authentication authentication = mock(Authentication.class);



    @Test
    void expectAllFoodSpots_whenAllFoodSpotsIsCalled() {
        //GIVEN
        FoodSpot firstTestFS = new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI", PriceLevel.LOW);
        FoodSpot secondTestFS = new FoodSpot("456", "Batman Restaurant", "Steindamm 58", "DOENER", PriceLevel.LOW);
        List<FoodSpot> expectedList = List.of(firstTestFS, secondTestFS);
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1",expectedList);
        //WHEN
        when(foodSpotUserRepo.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<FoodSpot> actualList = foodSpotService.allFoodSpots();
        //THEN
        verify(foodSpotUserRepo).findByUsername("sahed");
        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        assertEquals(expectedList, actualList);
    }

    @Test
    void expectNewFoodSpot_whenAddFoodSpotIsCalled() {
        //GIVEN
        FoodSpotWithoutId toAddFS = new FoodSpotWithoutId();
        toAddFS.setName("Luigi's");
        toAddFS.setAddress("Ditmar-Koel-Straße 21");
        toAddFS.setCategory("PIZZA");
        toAddFS.setPriceLevel(PriceLevel.LOW);

        FoodSpot expected = new FoodSpot("789", toAddFS.getName(), toAddFS.getAddress(), toAddFS.getCategory(), toAddFS.getPriceLevel());

        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1",new ArrayList<>(List.of(expected)));


        //WHEN
        when(idService.randomId()).thenReturn("789");
        when(foodSpotUserRepo.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(foodSpotUserRepo.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        FoodSpot actual = foodSpotService.addFoodSpot(toAddFS);
        //THEN
        verify(foodSpotUserRepo).findByUsername("sahed");
        verify(securityContext).getAuthentication();
        verify(authentication).getName();
        assertEquals(expected, actual);
    }

    @Test
    void expectSpecificFoodSpot_whenGetByIdIsCalled() {
        //GIVEN
        Optional<FoodSpot> expected = Optional.of(
                new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI", PriceLevel.LOW));
        String idToFind = "123";
        //WHEN
        when(foodSpotRepo.findById(idToFind)).thenReturn(expected);
        FoodSpot actual = foodSpotService.getById(idToFind);
        // Assert
        assertEquals(expected.get(), actual);
    }

    @Test
    void expectNoSuchElementException_whenGetByIdNotFound() {
        // GIVEN
        String idToFind = "nonexistent-id";
        // WHEN
        when(foodSpotRepo.findById(idToFind)).thenReturn(Optional.empty());
        // Assert
        assertThrows(NoSuchElementException.class, () -> foodSpotService.getById(idToFind));
    }

    @Test
    void expectNoSuchElementException_whenGetByIdWithNullId() {
        // GIVEN
        String nullId = null;
        // WHEN
        // No need to mock the repository since we expect the method to throw an exception directly
        // Assert
        assertThrows(NoSuchElementException.class, () -> foodSpotService.getById(nullId));
    }

    @Test
    void expectUpdatedFoodSpot_whenUpdateIsCalled() {
        // GIVEN
        FoodSpot expected = new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI", PriceLevel.LOW);
        FoodSpotWithoutId foodSpotWithoutId = new FoodSpotWithoutId();
        foodSpotWithoutId.setName(expected.getName());
        foodSpotWithoutId.setAddress(expected.getAddress());
        foodSpotWithoutId.setCategory(expected.getCategory());
        foodSpotWithoutId.setPriceLevel(expected.getPriceLevel());
        String idToUpdate = "123";

        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1",new ArrayList<>(List.of(expected)));
        when(foodSpotUserRepo.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(foodSpotUserRepo.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        // WHEN
        FoodSpot actual = foodSpotService.updateFoodSpot(idToUpdate, foodSpotWithoutId);
        // Assert
        verify(foodSpotUserRepo, times(2)).findByUsername("sahed");
        verify(securityContext, times(2)).getAuthentication();
        verify(authentication, times(2)).getName();
        assertEquals(expected, actual);
    }

    @Test
    void expectListOfFoodSpotsWithoutDeleted_whenDeleteIsCalled() {
        //GIVEN
        FoodSpot expected = new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI", PriceLevel.LOW);
        FoodSpotWithoutId foodSpotWithoutId = new FoodSpotWithoutId();
        foodSpotWithoutId.setName(expected.getName());
        foodSpotWithoutId.setAddress(expected.getAddress());
        foodSpotWithoutId.setCategory(expected.getCategory());
        String idToDelete = "123";
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1",new ArrayList<>(List.of(expected)));

        //WHEN
        when(foodSpotUserRepo.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(foodSpotUserRepo.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<FoodSpot> actual = foodSpotService.deleteFoodSpot(idToDelete);
        //THEN
        verify(foodSpotUserRepo, times(2)).findByUsername("sahed");
        verify(securityContext, times(2)).getAuthentication();
        verify(authentication, times(2)).getName();
        assertFalse(actual.contains(expected));
    }

    @Test
    void expectNoSuchElementException_whenDeleteIdIsNotExistent() {
        //GIVEN
        FoodSpot expected = new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI", PriceLevel.LOW);
        String idToDelete = "123";
        //WHEN
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1",new ArrayList<>(List.of(expected)));

        //WHEN
        when(foodSpotUserRepo.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        when(foodSpotUserRepo.save(currentUser)).thenReturn(currentUser);
        when(authentication.getName()).thenReturn("sahed");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(foodSpotRepo.findById(idToDelete)).thenReturn(Optional.of(expected));
        List<FoodSpot> actual = foodSpotService.deleteFoodSpot(idToDelete);
        //THEN
        assertThrows(NoSuchElementException.class, () -> foodSpotService.deleteFoodSpot("000"));
    }
}
