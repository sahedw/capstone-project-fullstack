package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.googlemaps.GoogleMapsConfig;
import com.github.sahedw.backend.models.FoodSpot;
import com.github.sahedw.backend.models.FoodSpotRepo;
import com.github.sahedw.backend.models.FoodSpotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class FoodSpotControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FoodSpotService foodSpotService;

    @Autowired
    FoodSpotRepo foodSpotRepo;

    @Test
    @DirtiesContext
    void expectAllFoodSpots_whenGetRequestForAllFoodSpots() throws Exception {
        FoodSpot firstTestFS = new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI");
        FoodSpot secondTestFS = new FoodSpot("456", "Batman Restaurant", "Steindamm 58", "DOENER");
        foodSpotRepo.insert(firstTestFS);
        foodSpotRepo.insert(secondTestFS);
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
                        "category": "DOENER"
                    }
                ]
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/foodSpot"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedList));
    }

    @Test
    void expectNewFoodSpot_whenPostRequestAddFoodSpot() throws Exception {
        String expectedFoodSpot = """
                    {
                        "name": "Batman Restaurant",
                        "address": "Steindamm 58",
                        "category": "DOENER"
                    }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/foodSpot")
                .contentType(MediaType.APPLICATION_JSON).content("""
                    {
                        "name": "Batman Restaurant",
                        "address": "Steindamm 58",
                        "category": "DOENER"
                    }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedFoodSpot));
    }

    @Test
    @DirtiesContext
    void expectSearchedFoodSpot_whenGetRequestWithIdFoodSpot() throws Exception {
        FoodSpot searchedTestFS = new FoodSpot("456", "Batman Restaurant", "Steindamm 58", "DOENER");
        foodSpotRepo.insert(searchedTestFS);
        String expectedFoodSpot = """
                    {
                        "id": "456",
                        "name": "Batman Restaurant",
                        "address": "Steindamm 58",
                        "category": "DOENER"
                    }
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/foodSpot/456"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedFoodSpot));
    }

    @Test
    @DirtiesContext
    void expectUpdatedFoodSpot_whenPutRequestWithFoodSpot() throws Exception {
        FoodSpot toUpdate = new FoodSpot("456", "Batman Restaurant", "Steindamm 58", "DOENER");
        foodSpotRepo.insert(toUpdate);
        String expectedFoodSpot = """
                    {
                        "id": "456",
                        "name": "Batman Restaurant - Updated",
                        "address": "Steindamm 58",
                        "category": "DOENER"
                    }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/foodSpot/456")
                        .contentType(MediaType.APPLICATION_JSON).content(
                        """
                    {
                        "id": "456",
                        "name": "Batman Restaurant - Updated",
                        "address": "Steindamm 58",
                        "category": "DOENER"
                    }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedFoodSpot));
    }
}
