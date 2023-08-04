package com.github.sahedw.backend.models;

import com.github.sahedw.backend.security.FoodSpotUser;
import com.github.sahedw.backend.security.FoodSpotUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodSpotService {

    private final FoodSpotRepo foodSpotRepo;
    private final IdService idService;
    private final FoodSpotUserRepo foodSpotUserRepo;


    private FoodSpotUser getUser() {
        Optional<FoodSpotUser> currentUser = foodSpotUserRepo.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser.isPresent()) {
            return currentUser.get();
        } else {
            throw new NoSuchElementException("No user logged in");
        }
    }

    private int getIndex(String id) {
        int foundIndex = -1;
        List<FoodSpot> ownFoodSpots = getUser().ownFoodSpots();
        for (FoodSpot foodSpot : ownFoodSpots) {
            if (foodSpot.getId().equals(id)) {
                foundIndex = ownFoodSpots.indexOf(foodSpot);
                break;
            }
        }

        if (foundIndex != -1) {
            return foundIndex;
        } else {
            throw new NoSuchElementException("Id not in your account");
        }
    }

    public List<FoodSpot> allFoodSpots() {
        FoodSpotUser currentUser = getUser();
        return currentUser.ownFoodSpots();
    }

    public FoodSpot addFoodSpot(FoodSpotWithoutId newFoodSpotDto) {
        FoodSpotUser currentUserToAddTo = getUser();
        FoodSpot newFoodSpot = new FoodSpot(
                idService.randomId(),
                newFoodSpotDto.getName(),
                newFoodSpotDto.getAddress(),
                newFoodSpotDto.getCategory(),
                newFoodSpotDto.getInstagramUsername(),
                newFoodSpotDto.getPriceLevel());
        currentUserToAddTo.ownFoodSpots().add(newFoodSpot);
        foodSpotUserRepo.save(currentUserToAddTo);
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
        FoodSpotUser currentUserToUpdate = getUser();
        FoodSpot newUpdatedFoodSpot = new FoodSpot(
                id,
                updatedFoodSpotDto.getName(),
                updatedFoodSpotDto.getAddress(),
                updatedFoodSpotDto.getCategory(),
                updatedFoodSpotDto.getInstagramUsername(),
                updatedFoodSpotDto.getPriceLevel());
        currentUserToUpdate.ownFoodSpots().set(getIndex(id), newUpdatedFoodSpot);
        foodSpotUserRepo.save(currentUserToUpdate);
        return newUpdatedFoodSpot;
    }

    public List<FoodSpot> deleteFoodSpot(String idToDelete) {
        FoodSpotUser currentUserToDeleteFrom = getUser();
        List<FoodSpot> listWithFoodSpotToDelete = new ArrayList<>();

        for (FoodSpot foodSpot : currentUserToDeleteFrom.ownFoodSpots()) {
            if (foodSpot.getId().equals(idToDelete)) {
                int index = currentUserToDeleteFrom.ownFoodSpots().indexOf(foodSpot);
                listWithFoodSpotToDelete.add(currentUserToDeleteFrom.ownFoodSpots().get(index));
            }
        }

        if (!listWithFoodSpotToDelete.isEmpty()) {
            currentUserToDeleteFrom.ownFoodSpots().remove(getIndex(idToDelete));
            foodSpotUserRepo.save(currentUserToDeleteFrom);
        } else {
            throw new NoSuchElementException("No FoodSpot with ID: " + idToDelete + " found.");
        }
        return currentUserToDeleteFrom.ownFoodSpots();
    }
}

