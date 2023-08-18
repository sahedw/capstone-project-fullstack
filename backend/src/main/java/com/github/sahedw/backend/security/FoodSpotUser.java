package com.github.sahedw.backend.security;

import com.github.sahedw.backend.models.FoodSpot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("users")
public record FoodSpotUser(
        @Id
        String id,

        @NotBlank
        @Size(min=5, max=50)
        String username,

        @NotBlank
        @Size(min=8, max=50)
        String password,

        @NotBlank
        @Size(min=2, max=50)
        String city,

        @NotBlank
        List<FoodSpot> ownFoodSpots,

        @NotBlank
        List<String> ownCategories,

        @NotBlank
        String seed
) {
}
