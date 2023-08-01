package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.models.FoodSpotWithoutId;
import com.github.sahedw.backend.models.FoodSpot;
import com.github.sahedw.backend.models.FoodSpotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public FoodSpot add(@Valid @RequestBody FoodSpotWithoutId foodSpotWithoutId) {
      return foodSpotService.addFoodSpot(foodSpotWithoutId);
    }

    @GetMapping("/{id}")
    public FoodSpot get(@PathVariable String id) {
       return foodSpotService.getById(id);
    }

    @PutMapping("/{id}")
    public FoodSpot update(@PathVariable String id, @Valid @RequestBody FoodSpotWithoutId updatedFoodSpotDto) {
        return foodSpotService.updateFoodSpot(id, updatedFoodSpotDto);
    }

    @DeleteMapping("/{id}")
    public List<FoodSpot> delete(@PathVariable String id) {
        return foodSpotService.deleteFoodSpot(id);
    }
}
