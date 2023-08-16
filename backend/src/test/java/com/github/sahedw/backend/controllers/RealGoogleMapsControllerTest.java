package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.googlemaps.GeocodeApiService;
import com.github.sahedw.backend.googlemaps.GoogleMapsConfig;
import com.github.sahedw.backend.googlemaps.GoogleMapsService;
import com.github.sahedw.backend.googlemaps.Position;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RealGoogleMapsControllerTest {


    @Autowired
    MockMvc mockMvc;

    static GoogleMapsService googleMapsService;

    static GoogleMapsConfig googleMapsConfig;



    @Test
    void expectApiKeyFromApplicationProperties_whenGetApiKeyGetsCalled() throws Exception {


        mockMvc.perform(get("/api/google/key"))

                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Test"));
    }

}