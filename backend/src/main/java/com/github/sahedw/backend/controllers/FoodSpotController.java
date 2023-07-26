package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.models.DtoFoodSpot;
import com.github.sahedw.backend.models.FoodSpot;
import com.github.sahedw.backend.models.FoodSpotService;
import com.github.sahedw.backend.models.IdService;
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
    public FoodSpot add(@RequestBody DtoFoodSpot dtoFoodSpot) {
      FoodSpot newFoodSpot = new FoodSpot(
              IdService.randomId(),
              dtoFoodSpot.getName(),
              dtoFoodSpot.getAddress(),
              dtoFoodSpot.getCategory());

      return foodSpotService.addFoodSpot(newFoodSpot);
    }

    @GetMapping("/{id}")
    public FoodSpot get(@PathVariable String id) {
       return foodSpotService.getById(id);
    }
}
