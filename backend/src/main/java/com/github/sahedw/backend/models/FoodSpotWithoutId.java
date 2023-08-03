package com.github.sahedw.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FoodSpotWithoutId {

    @NotBlank
    @Size(min=2, max=150)
    private String name;

    @NotBlank
    @Size(min=5, max=100)
    private String address;

    @NotBlank
    @Size(min=5, max=256)
    private String category;

    private String instagramUsername;

    private PriceLevel priceLevel;

}
