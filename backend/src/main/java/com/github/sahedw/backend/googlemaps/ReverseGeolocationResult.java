package com.github.sahedw.backend.googlemaps;

import java.util.List;

public record ReverseGeolocationResult(
List<Results> results
) {
}
