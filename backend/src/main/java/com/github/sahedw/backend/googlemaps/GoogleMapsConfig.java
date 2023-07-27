package com.github.sahedw.backend.googlemaps;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class GoogleMapsConfig {
    @Value("${google.maps.api.key}")
    private String apiKey;

    public String getKey() {
        return apiKey;
    }
}
