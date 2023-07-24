package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.models.FoodSpot;
import com.github.sahedw.backend.models.FoodSpotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/foodSpot")
public class FoodSpotController {

    private final FoodSpotService foodSpotService;

    public FoodSpotController(FoodSpotService foodSpotService) {
        this.foodSpotService = foodSpotService;
    }

    @GetMapping
    public List<FoodSpot> all() {
        return foodSpotService.allFoodSpots();
    }
}
