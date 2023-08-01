package com.github.sahedw.backend.security;

import org.springframework.data.annotation.Id;

public record FoodSpotUserForSignUp(
        String username,
        String password
) {
}
