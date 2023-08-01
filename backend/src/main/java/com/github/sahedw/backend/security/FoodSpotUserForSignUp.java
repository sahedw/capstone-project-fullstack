package com.github.sahedw.backend.security;


import jakarta.validation.constraints.NotBlank;

public record FoodSpotUserForSignUp(
        @NotBlank
        String username,

        @NotBlank
        String password
) {
}
