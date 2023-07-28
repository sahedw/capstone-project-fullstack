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

    private final IdService idService;

    public List<FoodSpot> allFoodSpots() {
        return foodSpotRepo.findAll();
    }

    public FoodSpot addFoodSpot(FoodSpotWithoutId newFoodSpotDto) {
        FoodSpot newFoodSpot = new FoodSpot(
                idService.randomId(),
                newFoodSpotDto.getName(),
                newFoodSpotDto.getAddress(),
                newFoodSpotDto.getCategory());
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

    public FoodSpot updateFoodSpot(String id, FoodSpotWithoutId updatedFoodSpotDto) {
        FoodSpot newUpdatedFoodSpot = new FoodSpot(
                id,
                updatedFoodSpotDto.getName(),
                updatedFoodSpotDto.getAddress(),
                updatedFoodSpotDto.getCategory());
        return foodSpotRepo.save(newUpdatedFoodSpot);
    }

    public List<FoodSpot> deleteFoodSpot(String idToDelete) {
        if (foodSpotRepo.findById(idToDelete).isPresent()) {
            Optional<FoodSpot> foundFoodSpot = foodSpotRepo.findById(idToDelete);
            foundFoodSpot.ifPresent(foodSpotRepo::delete);
        } else {
            throw new NoSuchElementException("No FoodSpot with ID: " + idToDelete + " found.");
        }
        return foodSpotRepo.findAll();
    }
}

