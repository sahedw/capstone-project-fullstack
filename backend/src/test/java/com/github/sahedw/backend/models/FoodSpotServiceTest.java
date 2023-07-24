package com.github.sahedw.backend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoodSpotServiceTest {

    FoodSpotRepo foodSpotRepo = mock(FoodSpotRepo.class);
    FoodSpotService foodSpotService = new FoodSpotService(foodSpotRepo);

    @Test
    void expectAllFoodSpots_whenAllFoodSpotsIsCalled() {
        //GIVEN

        //WHEN
        //THEN
    }

}