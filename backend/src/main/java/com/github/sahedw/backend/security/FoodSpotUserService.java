package com.github.sahedw.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodSpotUserService {

    private final FoodSpotUserRepo foodSpotUserRepo;

    private final PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    public String signUp(DtoQuizUser quizUser) {
        String hashedPassword = encoder.encode(quizUser.password());
        QuizUser newQuizUser = new QuizUser(IdService.uuid(), quizUser.username(), hashedPassword, List.of());
        quizUserRepo.insert(newQuizUser);
        return newQuizUser.username();
    }
}
