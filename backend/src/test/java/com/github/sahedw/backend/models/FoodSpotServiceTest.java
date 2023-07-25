package com.github.sahedw.backend.models;

import org.junit.jupiter.api.Test;
import java.util.List;

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
        List<FoodSpot> expectedList = List.of(firstTestFS,secondTestFS);
        //WHEN
        when(foodSpotRepo.findAll()).thenReturn(expectedList);
        List<FoodSpot> actualList = foodSpotService.allFoodSpots();
        //THEN
        verify(foodSpotRepo).findAll();
        assertEquals(expectedList, actualList);
    }

}
