package com.github.sahedw.backend.googlemaps;

import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoogleMapsServiceTest {

    static GoogleMapsConfig googleMapsConfig = mock(GoogleMapsConfig.class);
    static GeocodeApiService geocodeApiService = mock(GeocodeApiService.class);
    static GoogleMapsService googleMapsService;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void beforeAll() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        googleMapsService = new GoogleMapsService(mockWebServer.url("/").toString(), googleMapsConfig, geocodeApiService);
    }

    @Test
    void expectKeyString_whenGetApiKeyGetsCalled() {
        //GIVEN
        String expect = "abcdefghijklmnopqrstuvwxyz";
        //WHEN
        when(googleMapsConfig.getKey()).thenReturn(expect);
        String actual = googleMapsService.getKey();
        //THEN
        verify(googleMapsConfig, times(2)).getKey();
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
    @DirtiesContext
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
        assertThrows(NotFoundException.class, () -> googleMapsService.getGeocodeMulti(addresses));
    }

    @Test
    void expectNotFoundException_whenGetGeocodeMultiIsCalledWithResultsNull() throws IOException, InterruptedException, ApiException {
        //GIVEN
        List<String> addresses = new ArrayList<>();
        addresses.add("dsd");
        addresses.add("sdsd");
        Position expectedFirst = new Position("53.", "9.91507");
        //FIRST POSITION
        GeocodingResult geocodingResult = new GeocodingResult();
        Geometry geometry = new Geometry();
        geometry.location = new LatLng(0.00000000, 0.00000000);
        geocodingResult.geometry = geometry;
        GeocodingResult[] results = null;
        //SECOND POSITION
        GeocodingResult geocodingResultTwo = new GeocodingResult();
        Geometry geometryTwo = new Geometry();
        geometry.location = new LatLng(0.00000000, 0.00000000);
        geocodingResultTwo.geometry = geometry;
        GeocodingResult[] resultsTwo = null;

        //WHEN
        when(googleMapsConfig.getKey()).thenReturn("Test");
        when(geocodeApiService.geocode(addresses.get(0))).thenThrow(NotFoundException.class);

        //THEN
        assertThrows(NotFoundException.class, () -> googleMapsService.getGeocodeMulti(addresses));
    }

    @Test
    void expectRightAddress_whenGetAddressIsCalled() throws IOException {
        List<String> expected = new ArrayList<>(List.of("Fuhlsbütteler Straße 110"));
        Position positionOfAddress = new Position("53.5889494", "10.0460201");

        mockWebServer.enqueue(new MockResponse()
                .setHeader("Content-Type", "application/json")
                .setBody("""
                        {
                            "results": [
                                {
                                    "formatted_address": "Fuhlsbütteler Straße 110"
                                }
                            ]
                        }
                        """));

        when(googleMapsConfig.getKey()).thenReturn("Test");
        List<String> actual = googleMapsService.getAddress(positionOfAddress);

        verify(googleMapsConfig).getKey();
        assertEquals(expected, actual);
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }
}








