package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.exceptions.UsernameAlreadyExistsException;
import com.github.sahedw.backend.security.FoodSpotUser;
import com.github.sahedw.backend.security.FoodSpotUserForSignUp;
import com.github.sahedw.backend.security.FoodSpotUserRepo;
import com.github.sahedw.backend.security.FoodSpotUserService;
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

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class FoodSpotUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FoodSpotUserRepo foodSpotUserRepo;

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

    @Test
    @DirtiesContext
    void getNewUsername_whenSigningUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "franz",
                                    "password": "franz1234",
                                    "city": "Hamburg",
                                    "seed": "test"
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "franz"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(username = "sahed")
    void getUserCity_whenGetUserCity() throws Exception {
        FoodSpotUser existingUser = new FoodSpotUser("123", "sahed", "franz1234", "Hamburg", List.of(), "");
        foodSpotUserRepo.insert(existingUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/city")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "Hamburg"));
    }

    @Test
    @DirtiesContext
    void getUsernameAlreadyExistsException_whenSigningUpWithAlreadyExistingUsername() throws Exception {
        FoodSpotUser existingUser = new FoodSpotUser("123", "franz", "franz1234", "Hamburg", List.of(), "");
        foodSpotUserRepo.insert(existingUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "franz",
                                    "password": "franz1234",
                                    "city": "Hamburg",
                                    "seed": "test"
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message": "The username franz already exists."
                        }
                        """));


    }


    @Test
    void getException_whenSigningUpWithTooShortUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "s",
                                    "password": "franz19870",
                                    "city": "Hamburg",
                                    "seed": "test"
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                    "message": "size must be between 5 and 50"
                                }
                                        """));
    }

    @Test
    void getException_whenSigningUpWithBlankUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "        ",
                                    "password": "franz19870",
                                    "city": "Hamburg"
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                    "message": "must not be blank"
                                }
                                        """));
    }

    @Test
    void getException_whenSigningUpWithTooShortPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "franzi12345",
                                    "password": "fra",
                                    "city": "Hamburg",
                                    "seed": "test"
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                    "message": "size must be between 8 and 50"
                                }
                                        """));
    }

    @Test
    void getException_whenSigningUpWithBlankPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "username": "franzi12345",
                                    "password": "           ",
                                    "city": "Hamburg"
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                {
                                    "message": "must not be blank"
                                }
                                        """));
    }
}
