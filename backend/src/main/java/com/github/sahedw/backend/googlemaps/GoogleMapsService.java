package com.github.sahedw.backend.googlemaps;

import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<Position> allPositions = new ArrayList<>();

        for (String address : addressList) {
            GeocodingResult[] results = geocodeApiService.geocode(address);

            if (results != null)  {
                LatLng location = results[0].geometry.location;
                allPositions.add(new Position(String.valueOf(location.lat), String.valueOf(location.lng)));
            } else {
                throw new NotFoundException("No Position found");
            }
        }
        return allPositions;
    }

    public String getAddress(Position position) {
        WebClient webClient = WebClient.create("https://maps.googleapis.com/maps/api");

        ResponseEntity<ReverseGeolocationResult> requestEntity = webClient.get()
                .uri("/geocode/json?latlng=" + position.getLatitude() + "," + position.getLongitude() + "&key=" + googleMapsConfig.getKey())
                .retrieve()
                .toEntity(ReverseGeolocationResult.class)
                .block();

        assert requestEntity != null;
        return Objects.requireNonNull(Objects.requireNonNull(requestEntity.getBody()).results().get(0).formatted_address());
    }
}
