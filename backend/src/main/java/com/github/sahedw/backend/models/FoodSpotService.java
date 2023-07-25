package com.github.sahedw.backend.models;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodSpotService {

    private final FoodSpotRepo foodSpotRepo;

    public List<FoodSpot> allFoodSpots() {
       return foodSpotRepo.findAll();
    }

}

