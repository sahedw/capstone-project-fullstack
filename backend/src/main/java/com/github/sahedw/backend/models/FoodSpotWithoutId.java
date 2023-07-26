package com.github.sahedw.backend.models;

import lombok.Data;

@Data
public class FoodSpotWithoutId {
    private String id;
    private String name;
    private String address;
    private String category;
}
