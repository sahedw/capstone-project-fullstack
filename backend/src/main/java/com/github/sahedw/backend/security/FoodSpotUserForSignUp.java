package com.github.sahedw.backend.security;

import org.springframework.data.annotation.Id;

public record FoodSpotUserForSignUp(
        @Id
        String id,
        String username,
        String password
) {
}
