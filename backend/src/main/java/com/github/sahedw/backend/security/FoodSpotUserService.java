package com.github.sahedw.backend.security;

import com.github.sahedw.backend.exceptions.UsernameAlreadyExistsException;
import com.github.sahedw.backend.models.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodSpotUserService {

    private final FoodSpotUserRepo foodSpotUserRepo;

    private final IdService idService;

    private final PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public String signUp(FoodSpotUserForSignUp dtoUser) {
        Optional<FoodSpotUser> alreadyExistingUser = foodSpotUserRepo.findByUsername(dtoUser.username());
        if (alreadyExistingUser.isEmpty()) {
            String hashedPassword = encoder.encode(dtoUser.password());
            FoodSpotUser newFoodSpotUser = new FoodSpotUser(
                    idService.randomId(),
                    dtoUser.username(),
                    hashedPassword,
                    List.of());
            foodSpotUserRepo.insert(newFoodSpotUser);
            return newFoodSpotUser.username();
        } else {
            throw new UsernameAlreadyExistsException("The username " + dtoUser.username() + " already exists.");
        }
    }
}
