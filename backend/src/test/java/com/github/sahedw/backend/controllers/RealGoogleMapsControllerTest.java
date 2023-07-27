package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.googlemaps.GoogleMapsConfig;
import com.github.sahedw.backend.googlemaps.GoogleMapsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class RealGoogleMapsControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    GoogleMapsService googleMapsService;

    @Autowired
    GoogleMapsConfig googleMapsConfig;


    @Test
    void expectApiKeyFromApplicationProperties_whenGetApiKeyGetsCalled() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/api/google/key"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Test"));
    }

}