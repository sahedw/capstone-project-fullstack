package com.github.sahedw.backend.controllers;


import com.github.sahedw.backend.exceptions.ErrorMessage;
import com.github.sahedw.backend.exceptions.UsernameAlreadyExistsException;
import com.github.sahedw.backend.security.FoodSpotUserForSignUp;
import com.github.sahedw.backend.security.FoodSpotUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

    @GetMapping("/city")
    public String getUserCity() {
       return foodSpotUserService.getUserCity();
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @RequestBody FoodSpotUserForSignUp dtoUser) {
        return foodSpotUserService.signUp(dtoUser);
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handleUsernameAlreadyExistsExceptions(UsernameAlreadyExistsException exception) {
        return new ErrorMessage(exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException exception) {
        return new ErrorMessage(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
    }
}

