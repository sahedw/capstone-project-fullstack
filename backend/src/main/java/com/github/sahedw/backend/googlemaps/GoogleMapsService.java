package com.github.sahedw.backend.googlemaps;

import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if (results != null) {
            LatLng location = results[0].geometry.location;
            return new Position(String.valueOf(location.lat), String.valueOf(location.lng));
        } else {
            throw new NotFoundException("No Position found");
        }
    }

    public List<Position> getGeocodeMulti(List<String> addressList) throws IOException, InterruptedException, ApiException {
        List<GeocodingResult[]> resultsMulti = new ArrayList<>();
        List<Position> allPositions = new ArrayList<>();

        for (String address : addressList) {
            GeocodingResult[] results = geocodeApiService.geocode(address);

            if (results != null && results.length > 0) {
                LatLng location = results[0].geometry.location;
                allPositions.add(new Position(String.valueOf(location.lat), String.valueOf(location.lng)));
            } else {
                throw new NotFoundException("No Position found");
            }
        }
        return allPositions;
    }
}