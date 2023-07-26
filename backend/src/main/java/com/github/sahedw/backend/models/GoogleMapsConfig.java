package com.github.sahedw.backend.models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class GoogleMapsConfig {
    @Value("${google.maps.api.key}")
    private String apiKey;

    public String getKey() {
        return apiKey;
    }
}
