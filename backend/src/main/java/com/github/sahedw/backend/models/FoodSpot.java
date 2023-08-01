package com.github.sahedw.backend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("foodSpots")
public class FoodSpot {
    @Id
    private String id;

    @NotBlank
    @Size(min=2, max=150)
    private String name;

    @NotBlank
    @Size(min=5, max=100)
    private String address;

    @NotBlank
    @Size(min=5, max=256)
    private String category;

}

