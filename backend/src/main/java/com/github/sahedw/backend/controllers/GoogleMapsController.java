package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.models.GoogleMapsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/google/key")
public class GoogleMapsController {

    private final GoogleMapsService googleMapsService;

    public GoogleMapsController(GoogleMapsService googleMapsService) {
        this.googleMapsService = googleMapsService;
    }

    @GetMapping
    public String getKey() {
        return googleMapsService.getKey();
    }
}
