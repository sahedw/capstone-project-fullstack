package com.github.sahedw.backend.googlemaps;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoogleMapsServiceTest {

    GoogleMapsConfig googleMapsConfig = mock(GoogleMapsConfig.class);
    GeocodeApiService geocodeApiService = mock(GeocodeApiService.class);
    GoogleMapsService googleMapsService = new GoogleMapsService(googleMapsConfig, geocodeApiService);


    @Test
    void expectKeyString_whenGetApiKeyGetsCalled() {
        //GIVEN
        String expect = "abcdefghijklmnopqrstuvwxyz";
        //WHEN
        when(googleMapsConfig.getKey()).thenReturn(expect);
        String actual = googleMapsService.getKey();
        //THEN
        verify(googleMapsConfig).getKey();
        assertEquals(expect, actual);
    }

    @Test
    void expectRightPosition_whenGetGeocodeIsCalled() throws IOException, InterruptedException, ApiException {
        //GIVEN
        String address = "Gasstraße 6a, 22761 Hamburg";
        Position expected = new Position("53.56147", "9.91507");
        GeocodingResult geocodingResult = new GeocodingResult();
        Geometry geometry = new Geometry();
        geometry.location = new LatLng(53.56147, 9.91507);
        geocodingResult.geometry = geometry;
        GeocodingResult[] results = {geocodingResult};
        //WHEN
        when(googleMapsConfig.getKey()).thenReturn("Test");
        when(geocodeApiService.geocode(address)).thenReturn(results);
        Position actual = googleMapsService.getGeocode(address);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void expectNotFoundException_whenGeocodeWithBadAddress() throws IOException, InterruptedException, ApiException {
        Assertions.assertThrows(NotFoundException.class, () -> googleMapsService.getGeocode("Testtest"));
    }
}








