package com.github.sahedw.backend.controllers;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class FoodSpotUserController {
    @GetMapping("/account")
    public String getMe() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping("/login")
    public String login(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping("/sign-up")
    public String signUp(){
        return foodSpotUserService.signUp(dtoQuizUser);

    }
}

