package com.github.sahedw.backend.models;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodSpotServiceTest {

    IdService idService = mock(IdService.class);
    FoodSpotRepo foodSpotRepo = mock(FoodSpotRepo.class);
    FoodSpotService foodSpotService = new FoodSpotService(foodSpotRepo, idService);

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
        FoodSpotWithoutId toAddFS = new FoodSpotWithoutId();
        toAddFS.setName("Luigi's");
        toAddFS.setAddress("Ditmar-Koel-Straße 21");
        toAddFS.setCategory("PIZZA");
        FoodSpot expected = new FoodSpot("789", toAddFS.getName(), toAddFS.getAddress(), toAddFS.getCategory());
        //WHEN
        when(idService.randomId()).thenReturn("789");
        when(foodSpotRepo.insert(expected)).thenReturn(expected);
        FoodSpot actual = foodSpotService.addFoodSpot(toAddFS);
        //THEN
        verify(foodSpotRepo).insert(expected);
        assertEquals(expected, actual);
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

    @Test
    void expectUpdatedFoodSpot_whenUpdateIsCalled() {
        // GIVEN
        FoodSpot expected = new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI");
        FoodSpotWithoutId foodSpotWithoutId = new FoodSpotWithoutId();
        foodSpotWithoutId.setName(expected.getName());
        foodSpotWithoutId.setAddress(expected.getAddress());
        foodSpotWithoutId.setCategory(expected.getCategory());
        String idToUpdate = "123";
        // WHEN
        when(foodSpotRepo.save(expected)).thenReturn(expected);
        FoodSpot actual = foodSpotService.updateFoodSpot(idToUpdate, foodSpotWithoutId);
        // Assert
        verify(foodSpotRepo).save(expected);
        assertEquals(expected, actual);
    }
}
