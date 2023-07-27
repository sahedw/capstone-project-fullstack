package com.github.sahedw.backend.googlemaps;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapsConfig {
    @Value("${google.maps.api.key}")
    private String apiKey;

    public String getKey() {
        return apiKey;
    }
}
