package com.github.sahedw.backend.security;

import com.github.sahedw.backend.models.IdService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodSpotUserService {

    private final FoodSpotUserRepo foodSpotUserRepo;

    private final IdService idService;

    private final PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public String signUp(FoodSpotUserForSignUp dtoUser) {
        String hashedPassword = encoder.encode(dtoUser.password());
        FoodSpotUser newFoodSpotUser = new FoodSpotUser(
                idService.randomId(),
                dtoUser.username(),
                hashedPassword,
                List.of());
        foodSpotUserRepo.insert(newFoodSpotUser);
        return newFoodSpotUser.username();
    }
}
