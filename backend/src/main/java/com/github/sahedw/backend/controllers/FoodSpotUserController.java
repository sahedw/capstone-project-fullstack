package com.github.sahedw.backend.controllers;


import com.github.sahedw.backend.security.FoodSpotUserForSignUp;
import com.github.sahedw.backend.security.FoodSpotUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class FoodSpotUserController {

    private final FoodSpotUserService foodSpotUserService;

    public FoodSpotUserController(FoodSpotUserService foodSpotUserService) {
        this.foodSpotUserService = foodSpotUserService;
    }

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
    public String signUp(@RequestBody FoodSpotUserForSignUp dtoUser){
        return foodSpotUserService.signUp(dtoUser);
    }
}

