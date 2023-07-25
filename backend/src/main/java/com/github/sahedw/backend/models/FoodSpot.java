package com.github.sahedw.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("foodSpots")
public class FoodSpot {
    private String id;
    private String name;
    private String address;
    private String category;

}

