package com.github.sahedw.backend.controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sahedw.backend.exceptions.UsernameAlreadyExistsException;
import com.github.sahedw.backend.models.*;
import com.github.sahedw.backend.security.FoodSpotUser;
import com.github.sahedw.backend.security.FoodSpotUserForSignUp;
import com.github.sahedw.backend.security.FoodSpotUserRepo;
import com.github.sahedw.backend.security.FoodSpotUserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import java.io.File;
import java.util.*;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.bson.assertions.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class FoodSpotUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FoodSpotUserRepo foodSpotUserRepo;

    @MockBean
    FoodSpotUserService foodSpotUserService;

    @MockBean
    Cloudinary cloudinary;
    Uploader uploader = mock(Uploader.class);

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
        FoodSpotUser existingUser = new FoodSpotUser("123", "sahed", "franz1234", "Hamburg", List.of(), List.of(),"");
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
        FoodSpotUser existingUser = new FoodSpotUser("123", "franz", "franz1234", "Hamburg", List.of(), List.of(),"");
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

    @Test
    @WithMockUser(username = "sahed")
    @DirtiesContext
        void getSeed_whenGetSeedIsCalled() throws Exception {
        FoodSpotUser existingUser = new FoodSpotUser("123", "sahed", "franz1234", "Hamburg", List.of(), List.of(),"abcde");
        foodSpotUserRepo.insert(existingUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/picture-seed")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "abcde"));
    }

    @Test
    @WithMockUser(username = "sahed")
    @DirtiesContext
    void postSeed_whenUpdateSeedIsCalled() throws Exception {
        FoodSpotUser existingUser = new FoodSpotUser("123", "sahed", "franz1234", "Hamburg", List.of(), List.of(),"abcde");
        foodSpotUserRepo.insert(existingUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/picture-seed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "username": "sahed",
                                "seed": "fghij"
                                }
                                """)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(
                        "fghij"));
    }

    @Test
    @WithMockUser(username = "sahed")
    @DirtiesContext
    void getCategories_whenGetCategoriesCalled() throws Exception {
        Category category1 = new Category("BURGER", new ImageDetails(new CategoryCSSDetails(70, 15, 100, "test1BG.de"), new FoodSpotCSSDetails(100, "test1Normal.de")));
        Category category2 = new Category("PIZZA", new ImageDetails(new CategoryCSSDetails(60, 25, 120, "test2BG.de"), new FoodSpotCSSDetails(110, "test2Normal.de")));
        FoodSpotUser existingUser = new FoodSpotUser("123", "sahed", "franz1234", "Hamburg", List.of(), List.of(category1, category2),"abcde");
        foodSpotUserRepo.insert(existingUser);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/categories")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                            {
                                "name": "BURGER",
                                "imageCSSDetails": {
                                    "categoryCard": {
                                        "leftPixel": 70,
                                        "topPixel": 15,
                                        "imageWidth": 100,
                                        "cloudinaryUrl": "test1BG.de"
                                    },
                                    "foodSpotCard": {
                                        "imageWidth": 100,
                                        "cloudinaryUrl": "test1Normal.de"
                                    }
                                }
                            },
                            {
                                "name": "PIZZA",
                                "imageCSSDetails": {
                                    "categoryCard": {
                                        "leftPixel": 60,
                                        "topPixel": 25,
                                        "imageWidth": 120,
                                        "cloudinaryUrl": "test2BG.de"
                                    },
                                    "foodSpotCard": {
                                        "imageWidth": 110,
                                        "cloudinaryUrl": "test2Normal.de"
                                    }
                                }
                            }
                        ]
                        """));
    }

    @Test
    @WithMockUser(username = "sahed")
    @DirtiesContext
    void addCategory_whenAddCategoryIsCalled() throws Exception {
        Category category1 = new Category("BURGER", new ImageDetails(new CategoryCSSDetails(70, 15, 100, "test1BG.de"), new FoodSpotCSSDetails(100, "test1Normal.de")));
        Category category2 = new Category("PIZZA", new ImageDetails(new CategoryCSSDetails(60, 25, 120, "test2BG.de"), new FoodSpotCSSDetails(110, "test2Normal.de")));
        List<Category> beforeUpdateList = new ArrayList<>();
        beforeUpdateList.add(category1);
        FoodSpotUser existingUser = new FoodSpotUser("123", "sahed", "franz1234", "Hamburg", List.of(), beforeUpdateList,"abcde");
        foodSpotUserRepo.insert(existingUser);

        when(foodSpotUserService.addUserCategories(any(), any(), any())).thenReturn(List.of(category1, category2));
        MockMultipartFile data = new MockMultipartFile("data",
                null,
                MediaType.APPLICATION_JSON_VALUE,
                """
                             {
                                "name": "PIZZA",
                                "imageCSSDetails": {
                                    "categoryCard": {
                                        "leftPixel": 60,
                                        "topPixel": 25,
                                        "imageWidth": 120,
                                        "cloudinaryUrl": "test2BG.de"
                                    },
                                    "foodSpotCard": {
                                        "imageWidth": 110,
                                        "cloudinaryUrl": "test2Normal.de"
                                    }
                                }
                            }
                        """
                        .getBytes()
        );
        MockMultipartFile bgImage = new MockMultipartFile("BGImage",
                "testImage.png",
                MediaType.IMAGE_PNG_VALUE,
                "testImage".getBytes()
        );

        MockMultipartFile normalImage = new MockMultipartFile("NormalImage",
                "testImage.png",
                MediaType.IMAGE_PNG_VALUE,
                "testImage".getBytes()
        );

        File fileToUpload = File.createTempFile("image", null);
        bgImage.transferTo(fileToUpload);
        normalImage.transferTo(fileToUpload);


        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(), any())).thenReturn(Map.of("url", "test-url"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/user/categories")
                        .file(data)
                        .file(bgImage)
                        .file(normalImage)
                        .with(csrf()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                            {
                                "name": "BURGER",
                                "imageCSSDetails": {
                                    "categoryCard": {
                                        "leftPixel": 70,
                                        "topPixel": 15,
                                        "imageWidth": 100,
                                        "cloudinaryUrl": "test1BG.de"
                                    },
                                    "foodSpotCard": {
                                        "imageWidth": 100,
                                        "cloudinaryUrl": "test1Normal.de"
                                    }
                                }
                            },
                            {
                                "name": "PIZZA",
                                "imageCSSDetails": {
                                    "categoryCard": {
                                        "leftPixel": 60,
                                        "topPixel": 25,
                                        "imageWidth": 120,
                                        "cloudinaryUrl": "test2BG.de"
                                    },
                                    "foodSpotCard": {
                                        "imageWidth": 110,
                                        "cloudinaryUrl": "test2Normal.de"
                                    }
                                }
                            }
                        ]
                        """));

    }
}
