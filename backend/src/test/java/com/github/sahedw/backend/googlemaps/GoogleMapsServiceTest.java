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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    void expectRightPositions_whenGetGeocodeMultiIsCalled() throws IOException, InterruptedException, ApiException {
        //GIVEN
        List<String> addresses = new ArrayList<>();
        addresses.add("Gasstraße 6a, 22761 Hamburg");
        addresses.add("Fuhlsbüttler Straße 110");
        Position expectedFirst = new Position("53.56147", "9.91507");
        Position expectedSecond = new Position("53.5889494", "10.0460201");
        //FIRST POSITION
        GeocodingResult geocodingResult = new GeocodingResult();
        Geometry geometry = new Geometry();
        geometry.location = new LatLng(53.56147, 9.91507);
        geocodingResult.geometry = geometry;
        GeocodingResult[] results = {geocodingResult};
        //SECOND POSITION
        GeocodingResult geocodingResultTwo = new GeocodingResult();
        Geometry geometryTwo = new Geometry();
        geometry.location = new LatLng(53.5889494, 10.0460201);
        geocodingResultTwo.geometry = geometry;
        GeocodingResult[] resultsTwo = {geocodingResultTwo};

        //WHEN
        when(googleMapsConfig.getKey()).thenReturn("Test");
        when(geocodeApiService.geocode(addresses.get(0))).thenReturn(results);
        when(geocodeApiService.geocode(addresses.get(1))).thenReturn(resultsTwo);
        List<Position> actual = googleMapsService.getGeocodeMulti(addresses);
        //THEN
        assertEquals(expectedSecond, actual.get(1));
    }

    @Test
    void expectNotFoundException_whenGeocodeWithBadAddress() throws IOException, InterruptedException, ApiException {
        Assertions.assertThrows(NotFoundException.class, () -> googleMapsService.getGeocode("Testtest"));
    }

    @Test
    void expectNotFoundException_whenGetGeocodeMultiIsCalledWithBadAddress() throws IOException, InterruptedException, ApiException {
        //GIVEN
        List<String> addresses = new ArrayList<>();
        addresses.add("dsd");
        addresses.add("sdsd");
        Position expectedFirst = new Position("53.56147", "9.91507");
        //FIRST POSITION
        GeocodingResult geocodingResult = new GeocodingResult();
        Geometry geometry = new Geometry();
        geometry.location = new LatLng(0.00000000, 0.00000000);
        geocodingResult.geometry = geometry;
        GeocodingResult[] results = {geocodingResult};
        //SECOND POSITION
        GeocodingResult geocodingResultTwo = new GeocodingResult();
        Geometry geometryTwo = new Geometry();
        geometry.location = new LatLng(0.00000000, 0.00000000);
        geocodingResultTwo.geometry = geometry;
        GeocodingResult[] resultsTwo = {geocodingResultTwo};

        //WHEN
        when(googleMapsConfig.getKey()).thenReturn("Test");
        when(geocodeApiService.geocode(addresses.get(0))).thenReturn(results);
        when(geocodeApiService.geocode(addresses.get(1))).thenThrow(NotFoundException.class);

        //THEN
        assertThrows(NotFoundException.class, () ->  googleMapsService.getGeocodeMulti(addresses));
    }
}








