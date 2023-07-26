package com.github.sahedw.backend.models;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleMapsService {

    private final GoogleMapsConfig googleMapsConfig;
    public String getKey() {
       return googleMapsConfig.getKey();
    }
}
