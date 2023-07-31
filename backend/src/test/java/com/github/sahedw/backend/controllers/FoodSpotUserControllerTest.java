package com.github.sahedw.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class FoodSpotUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAnonymousUser_whenEndpointIsCalled() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/account"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "anonymousUser"));
    }

    @Test
    @WithMockUser(username = "sahed")
    void getUsername_whenEndpointIsCalled() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/account"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "sahed"));
    }

    @Test
    @WithMockUser(username = "sahed")
    void getUsername_whenLoggingIn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "sahed"));
    }
}