package com.github.sahedw.backend.models;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodSpotRepo extends MongoRepository<FoodSpot, String> {
}

