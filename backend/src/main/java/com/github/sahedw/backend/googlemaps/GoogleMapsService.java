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

    private final GoogleMapsConfig googleMapsConfig;

    private final GeocodeApiService geocodeApiService;

    public String getKey() {
        return googleMapsConfig.getKey();
    }

    public Position getGeocode(String address) throws IOException, InterruptedException, ApiException {
        GeocodingResult[] results = geocodeApiService.geocode(address);
        if (results != null && results.length != 0) {
            LatLng location = results[0].geometry.location;
            return new Position(String.valueOf(location.lat),String.valueOf(location.lng));
        } else {
            throw new NotFoundException("No Position found");
        }
    }
}
