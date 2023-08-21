package com.github.sahedw.backend.controllers;


import com.github.sahedw.backend.exceptions.ErrorMessage;
import com.github.sahedw.backend.exceptions.UsernameAlreadyExistsException;
import com.github.sahedw.backend.models.Category;
import com.github.sahedw.backend.security.FoodSpotUserForSignUp;
import com.github.sahedw.backend.security.FoodSpotUserOnlyUsernameAndSeed;
import com.github.sahedw.backend.security.FoodSpotUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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

    @GetMapping("/picture-seed")
    public String getUserSeed() {
        return foodSpotUserService.getUserSeed();
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

    @PostMapping("/picture-seed")
    public String updateSeed(@RequestBody FoodSpotUserOnlyUsernameAndSeed dtoUser) {
        return foodSpotUserService.setSeed(dtoUser);
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return foodSpotUserService.getUserCategories();
    }

    @PostMapping("/categories")
    public List<Category> addCategories(@RequestPart(name = "data") Category category, @RequestPart(name = "BGImage") MultipartFile bgImage, @RequestPart(name = "NormalImage") MultipartFile normalImage) throws IOException {
        return foodSpotUserService.addUserCategories(category, bgImage, normalImage);
    }

    @DeleteMapping("/categories/{id}")
    public List<Category> addCategories(@PathVariable String id) {
        return foodSpotUserService.deleteCategory(id);
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

