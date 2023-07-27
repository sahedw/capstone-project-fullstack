package com.github.sahedw.backend.models;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodSpotServiceTest {

    FoodSpotRepo foodSpotRepo = mock(FoodSpotRepo.class);
    FoodSpotService foodSpotService = new FoodSpotService(foodSpotRepo);

    @Test
    void expectAllFoodSpots_whenAllFoodSpotsIsCalled() {
        //GIVEN
        FoodSpot firstTestFS = new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI");
        FoodSpot secondTestFS = new FoodSpot("456", "Batman Restaurant", "Steindamm 58", "DOENER");
        List<FoodSpot> expectedList = List.of(firstTestFS, secondTestFS);
        //WHEN
        when(foodSpotRepo.findAll()).thenReturn(expectedList);
        List<FoodSpot> actualList = foodSpotService.allFoodSpots();
        //THEN
        verify(foodSpotRepo).findAll();
        assertEquals(expectedList, actualList);
    }

    @Test
    void expectNewFoodSpot_whenAddFoodSpotIsCalled() {
        //GIVEN
        FoodSpot toAddFS = new FoodSpot("789", "Luigi's", "Ditmar-Koel-Straße 21", "PIZZA");
        //WHEN
        when(foodSpotRepo.insert(toAddFS)).thenReturn(toAddFS);
        FoodSpot actual = foodSpotService.addFoodSpot(toAddFS);
        //THEN
        verify(foodSpotRepo).insert(toAddFS);
        assertEquals(toAddFS, actual);
    }

    @Test
    void expectSpecificFoodSpot_whenGetByIdIsCalled() {
        //GIVEN
        Optional<FoodSpot> expected = Optional.of(
                new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI"));
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
}
