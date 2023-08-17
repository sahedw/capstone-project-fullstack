package com.github.sahedw.backend;

import com.github.sahedw.backend.googlemaps.GoogleMapsService;
import com.github.sahedw.backend.models.FoodSpotService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    FoodSpotService foodSpotService;
    @Test
    void contextLoads() {
        Assertions.assertNotNull(foodSpotService);
    }
}
