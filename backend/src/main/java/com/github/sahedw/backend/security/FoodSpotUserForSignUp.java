package com.github.sahedw.backend.security;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FoodSpotUserForSignUp(
        @NotBlank
        @Size(min=5, max=50)
        String username,

        @NotBlank
        @Size(min=8, max=50)
        String password
) {
}
