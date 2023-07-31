package com.github.sahedw.backend.security;

import com.github.sahedw.backend.models.FoodSpot;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("users")
public record FoodSpotUser(
        @Id
        String id,

        String username,

        String password,

        List<FoodSpot> ownFoodSpots
) {
}
