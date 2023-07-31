package com.github.sahedw.backend.security;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodSpotUserRepo extends MongoRepository<FoodSpotUser, String> {
    Optional<FoodSpotUser> findByUsername(String username);
}
