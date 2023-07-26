package com.github.sahedw.backend.googlemaps;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Position {
    private String latitude;
    private String longitude;

    public Position(String latitude, String longitude) {
    }
}
