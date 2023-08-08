package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.googlemaps.GoogleMapsConfig;
import com.github.sahedw.backend.googlemaps.GoogleMapsService;
import com.github.sahedw.backend.googlemaps.Position;
import com.github.sahedw.backend.security.FoodSpotUserDetailsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class MockedGoogleMapsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    GoogleMapsService googleMapsService;

    @MockBean
    FoodSpotUserDetailsService foodSpotUserDetailsService;

    @Autowired
    GoogleMapsConfig googleMapsConfig;

    @Test
    @WithMockUser(username = "sahed")
    void expectPosition_whenPostRequestAddressToGetGeocode() throws Exception {
        Position neueFischePosition = new Position("53.56147", "9.91507");
        Mockito.when(googleMapsService.getGeocode("Gasstraße 6a, 22761 Hamburg")).thenReturn(neueFischePosition);
        String expectedFoodSpot = """
                   {
                       "latitude": "53.56147",
                       "longitude": "9.91507"
                   }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/google/convert-address")
                        .content("Gasstraße 6a, 22761 Hamburg")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedFoodSpot));
    }

    @Test
    @WithMockUser(username = "sahed")
    void expectPositions_whenPostRequestAddressToGetGeocodeMulti() throws Exception {
        Position neueFischePosition = new Position("53.56147", "9.91507");
        Position senchaSushi = new Position("53.5889494", "10.0460201");
        List<Position> responseList = new ArrayList<>(List.of(neueFischePosition, senchaSushi));
        List<String> requestList = new ArrayList<>(List.of("Gasstraße 6a, 22761 Hamburg", "Fuhlsbüttler Straße 110"));
        Mockito.when(googleMapsService.getGeocodeMulti(requestList)).thenReturn(responseList);
        String expectedFoodSpot = """
                   [
                        {
                            "latitude": "53.56147",
                            "longitude": "9.91507"
                        },
                         {
                            "latitude": "53.5889494",
                            "longitude": "10.0460201"
                        }
                   ]

                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/google/convert-address-multi")
                        .contentType(MediaType.APPLICATION_JSON).content("""
                                ["Gasstraße 6a, 22761 Hamburg", "Fuhlsbüttler Straße 110"]
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedFoodSpot));
    }

}