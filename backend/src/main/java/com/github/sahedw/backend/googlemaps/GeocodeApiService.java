package com.github.sahedw.backend.googlemaps;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GeocodeApiService {

    private final GoogleMapsConfig googleMapsConfig;

    public GeocodingResult[] geocode(String address) throws IOException, InterruptedException, ApiException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(googleMapsConfig.getKey())
                .build();
        GeocodingResult[] results = GeocodingApi.geocode(context, address)
                .await();
        context.shutdown();
        return results;
    }
}
