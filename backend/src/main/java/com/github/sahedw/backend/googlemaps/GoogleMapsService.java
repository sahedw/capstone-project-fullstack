package com.github.sahedw.backend.googlemaps;

import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GoogleMapsService {

    private final GoogleMapsConfig googleMapsConfig;

    private final GeocodeApiService geocodeApiService;

    private final WebClient webClient;

    public GoogleMapsService(
            @Value("${google.maps.api.url}") String url,
            GoogleMapsConfig googleMapsConfig,
            GeocodeApiService geocodeApiService1) {

        this.webClient = WebClient.create(url);
        this.googleMapsConfig = googleMapsConfig;
        this.geocodeApiService = geocodeApiService1;
    }

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

            if (results != null) {
                LatLng location = results[0].geometry.location;
                allPositions.add(new Position(String.valueOf(location.lat), String.valueOf(location.lng)));
            } else {
                throw new NotFoundException("No Position found");
            }
        }
        return allPositions;
    }

    public List<String> getAddress(Position position) {
        List<String> allAddressResults = new ArrayList<>();

        ResponseEntity<ReverseGeolocationResult> requestEntity = webClient.get()
                .uri("/geocode/json?latlng=" + position.getLatitude() + "," + position.getLongitude() + "&key=" + googleMapsConfig.getKey())
                .retrieve()
                .toEntity(ReverseGeolocationResult.class)
                .block();

        assert requestEntity != null;

        for (int i = 0; i < Objects.requireNonNull(requestEntity.getBody()).results().size(); i++) {
            if (!Objects.requireNonNull(requestEntity.getBody()).results().get(i).formatted_address().contains("+") && !allAddressResults.contains(Objects.requireNonNull(requestEntity.getBody()).results().get(i).formatted_address()) && allAddressResults.size() != 5) {
                allAddressResults.add(Objects.requireNonNull(requestEntity.getBody()).results().get(i).formatted_address());
            }
        }
        return allAddressResults;
    }
}
