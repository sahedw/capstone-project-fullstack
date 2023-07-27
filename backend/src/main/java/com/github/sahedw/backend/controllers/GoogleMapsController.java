package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.googlemaps.GoogleMapsService;
import com.github.sahedw.backend.googlemaps.Position;
import com.google.maps.errors.ApiException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/google")
public class GoogleMapsController {

    private final GoogleMapsService googleMapsService;

    public GoogleMapsController(GoogleMapsService googleMapsService) {
        this.googleMapsService = googleMapsService;
    }

    @GetMapping("/key")
    public String getKey() {
        return googleMapsService.getKey();
    }

    @PostMapping("/convert-address")
    public Position getGeocode(@RequestBody String address) throws IOException, InterruptedException, ApiException {
        return googleMapsService.getGeocode(address);
    }
}
