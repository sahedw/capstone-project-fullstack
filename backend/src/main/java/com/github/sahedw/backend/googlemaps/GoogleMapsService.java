package com.github.sahedw.backend.googlemaps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class GoogleMapsService {

    @Autowired
    private final GoogleMapsConfig googleMapsConfig;

    public String getKey() {
        return googleMapsConfig.getKey();
    }

    public Position getGeocode(String address) throws IOException, InterruptedException, ApiException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(googleMapsConfig.getKey())
                .build();
        GeocodingResult[] results = GeocodingApi.geocode(context, address)
                .await();
        if (results.length != 0) {
            LatLng location = results[0].geometry.location;
            Position positionOfAddress = new Position();
            positionOfAddress.setLatitude(String.valueOf(location.lat));
            positionOfAddress.setLongitude(String.valueOf(location.lng));
            context.shutdown();
            return positionOfAddress;
        }
        throw new NotFoundException("No Position found");
    }
}
