package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.exceptions.ErrorMessage;
import com.github.sahedw.backend.googlemaps.GoogleMapsConfig;
import com.github.sahedw.backend.models.*;
import com.github.sahedw.backend.security.FoodSpotUser;
import com.github.sahedw.backend.security.FoodSpotUserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class FoodSpotControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FoodSpotService foodSpotService;

    @Autowired
    FoodSpotRepo foodSpotRepo;

    @MockBean
    FoodSpotUserRepo foodSpotUserRepo;

    @Test
    @DirtiesContext
    @WithMockUser(username = "sahed")
    void expectAllFoodSpots_whenGetRequestForAllFoodSpots() throws Exception {
        FoodSpot firstTestFS = new FoodSpot("123", "Sencha Sushi", "Fuhlsbüttler Str. 110", "SUSHI", PriceLevel.LOW);
        FoodSpot secondTestFS = new FoodSpot("456", "Batman Restaurant", "Steindamm 58", "DOENER", PriceLevel.LOW);

        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", List.of(firstTestFS, secondTestFS));

        Mockito.when(foodSpotUserRepo.findByUsername("sahed")).thenReturn(Optional.of(currentUser));


        String expectedList = """
                [
                    {
                        "id": "123",
                        "name": "Sencha Sushi",
                        "address": "Fuhlsbüttler Str. 110",
                        "category": "SUSHI",
                        "priceLevel": "LOW"
                    },
                    {
                        "id": "456",
                        "name": "Batman Restaurant",
                        "address": "Steindamm 58",
                        "category": "DOENER",
                        "priceLevel": "LOW"
                    }
                ]
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/foodSpot")
                        .with(csrf()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedList));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "sahed")
    void expectNewFoodSpot_whenPostRequestAddFoodSpot() throws Exception {
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", new ArrayList<>());

        Mockito.when(foodSpotUserRepo.findByUsername("sahed")).thenReturn(Optional.of(currentUser));

        String expectedFoodSpot = """
                    {
                        "name": "Batman Restaurant",
                        "address": "Steindamm 58",
                        "category": "DOENER",
                        "priceLevel": "LOW"
                    }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/foodSpot")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                    {
                                        "name": "Batman Restaurant",
                                        "address": "Steindamm 58",
                                        "category": "DOENER",
                                        "priceLevel": "LOW"
                                    }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedFoodSpot));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "sahed")
    void expectSearchedFoodSpot_whenGetRequestWithIdFoodSpot() throws Exception {
        FoodSpot searchedTestFS = new FoodSpot("456", "Batman Restaurant", "Steindamm 58", "DOENER", PriceLevel.LOW);
        foodSpotRepo.insert(searchedTestFS);
        String expectedFoodSpot = """
                    {
                        "id": "456",
                        "name": "Batman Restaurant",
                        "address": "Steindamm 58",
                        "category": "DOENER"
                    }
                """;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/foodSpot/456")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedFoodSpot));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "sahed")
    void expectUpdatedFoodSpot_whenPutRequestWithFoodSpot() throws Exception {
        FoodSpot toUpdate = new FoodSpot("456", "Batman Restaurant", "Steindamm 58", "DOENER", PriceLevel.LOW);
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", new ArrayList<>(List.of(toUpdate)));

        Mockito.when(foodSpotUserRepo.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        String expectedFoodSpot = """
                    {
                        "id": "456",
                        "name": "Batman Restaurant - Updated",
                        "address": "Steindamm 58",
                        "category": "DOENER",
                        "priceLevel": "LOW"
                    }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/foodSpot/456")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON).content(
                                """
                                            {
                                                "id": "456",
                                                "name": "Batman Restaurant - Updated",
                                                "address": "Steindamm 58",
                                                "category": "DOENER",
                                                "priceLevel": "LOW"
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedFoodSpot));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "sahed")
    void expectDeletedFoodSpot_whenDeleteRequestIsCalled() throws Exception {
        FoodSpot toDelete = new FoodSpot("456", "Batman Restaurant", "Steindamm 58", "DOENER", PriceLevel.LOW);
        FoodSpotUser currentUser = new FoodSpotUser("123", "sahed", "sahed1", new ArrayList<>(List.of(toDelete)));

        Mockito.when(foodSpotUserRepo.findByUsername("sahed")).thenReturn(Optional.of(currentUser));
        String expectedFoodSpotsList = """
                    [
                    ]
                """;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/foodSpot/456")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedFoodSpotsList));
    }

    @Test
    @WithMockUser(username = "sahed")
    void expectMethodArgumentNotValidException_whenNameTooShortPostRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/foodSpot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "b",
                                    "address": "Steindamm 58",
                                    "category": "DOENER"
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message": "size must be between 2 and 150"
                        }
                        """));
    }

    @Test
    @WithMockUser(username = "sahed")
    void expectMethodArgumentNotValidException_whenBlankNamePostRequest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/foodSpot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "   ",
                                    "address": "Steindamm 58",
                                    "category": "DOENER"
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message": "must not be blank"
                        }
                        """));
    }
}
