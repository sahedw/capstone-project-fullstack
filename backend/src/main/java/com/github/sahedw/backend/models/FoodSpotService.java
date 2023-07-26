package com.github.sahedw.backend.models;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodSpotService {

    private final FoodSpotRepo foodSpotRepo;

    public List<FoodSpot> allFoodSpots() {
       return foodSpotRepo.findAll();
    }

    public FoodSpot addFoodSpot(FoodSpot newFoodSpot) {
        foodSpotRepo.insert(newFoodSpot);
        return newFoodSpot;
    }

    public FoodSpot getById(String id) {
        Optional<FoodSpot> foundFoodSpot = foodSpotRepo.findById(id);
        if (foundFoodSpot.isPresent()) {
            return foundFoodSpot.get();
        } else {
            throw new NoSuchElementException();
        }
    }
}

