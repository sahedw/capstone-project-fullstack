package com.github.sahedw.backend.security;

import com.github.sahedw.backend.exceptions.UsernameAlreadyExistsException;
import com.github.sahedw.backend.models.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodSpotUserService {

    private final FoodSpotUserRepo foodSpotUserRepo;

    private final IdService idService;

    private final PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    private static final String NO_USER_EXCEPTION = "No user logged in.";

    public String signUp(FoodSpotUserForSignUp dtoUser) {
        Optional<FoodSpotUser> alreadyExistingUser = foodSpotUserRepo.findByUsername(dtoUser.username());
        if (alreadyExistingUser.isEmpty()) {
            String hashedPassword = encoder.encode(dtoUser.password());
            FoodSpotUser newFoodSpotUser = new FoodSpotUser(
                    idService.randomId(),
                    dtoUser.username(),
                    hashedPassword,
                    dtoUser.city(),
                    List.of(),
                    dtoUser.seed());
            foodSpotUserRepo.insert(newFoodSpotUser);
            return newFoodSpotUser.username();
        } else {
            throw new UsernameAlreadyExistsException("The username " + dtoUser.username() + " already exists.");
        }
    }

    public String getUserCity() {
        Optional<FoodSpotUser> requestingUser = foodSpotUserRepo.findByUsername(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());
        if (requestingUser.isPresent()) {
            return requestingUser.get().city();
        } else {
            throw new NoSuchElementException(NO_USER_EXCEPTION);
        }
    }

    public String setSeed(FoodSpotUserOnlyUsernameAndSeed dtoUser) {
        Optional<FoodSpotUser> toUpdateUser = foodSpotUserRepo.findByUsername(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());
        if (toUpdateUser.isPresent()) {
            FoodSpotUser updatedUser = new FoodSpotUser(
                    toUpdateUser.get().id(),
                    toUpdateUser.get().username(),
                    toUpdateUser.get().password(),
                    toUpdateUser.get().city(),
                    toUpdateUser.get().ownFoodSpots(),
                    dtoUser.seed()
            );
            foodSpotUserRepo.save(updatedUser);
            return updatedUser.seed();
        } else {
            throw new NoSuchElementException(NO_USER_EXCEPTION);
        }
    }

    public String getUserSeed() {
        Optional<FoodSpotUser> requiredUser = foodSpotUserRepo.findByUsername(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());
        if (requiredUser.isPresent()) {
            return requiredUser.get().seed();
        } else {
            throw new NoSuchElementException(NO_USER_EXCEPTION);
        }
    }
}
