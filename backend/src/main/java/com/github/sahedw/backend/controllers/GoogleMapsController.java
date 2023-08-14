package com.github.sahedw.backend.controllers;

import com.github.sahedw.backend.googlemaps.GoogleMapsService;
import com.github.sahedw.backend.googlemaps.Position;
import com.google.maps.errors.ApiException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/convert-address-multi")
    public List<Position> getGeocode(@RequestBody List<String> address) throws IOException, InterruptedException, ApiException {
        return googleMapsService.getGeocodeMulti(address);
    }

    @PostMapping("/convert-latlng")
    public String getAddress(@RequestBody Position position) {
        return googleMapsService.getAddress(position);
    }
}
