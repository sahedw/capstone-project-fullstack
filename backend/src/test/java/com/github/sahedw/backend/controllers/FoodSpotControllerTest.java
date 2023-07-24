package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.models.FoodSpotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
class FoodSpotControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FoodSpotService foodSpotService;

    @Test
    void expectAllFoodSpots_whenGetRequestForAllFoodSpots() throws Exception {
        String expectedList = """
                [
                    {
                        "id": "123",
                        "name": "Sencha Sushi",
                        "address": "Fuhlsbüttler Str. 110",
                        "category": "SUSHI"
                    },
                    {
                        "id": "456",
                        "name": "Batman Restaurant",
                        "address": "Steindamm 58",
                        "category": "DÖNER"
                    }
                ]
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/foodSpot"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedList));
    }
}
