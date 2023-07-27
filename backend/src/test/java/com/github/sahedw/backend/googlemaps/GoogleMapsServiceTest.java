package com.github.sahedw.backend.googlemaps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoogleMapsServiceTest {

    GoogleMapsConfig googleMapsConfig = mock(GoogleMapsConfig.class);

    GoogleMapsService googleMapsService = new GoogleMapsService(googleMapsConfig);


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

}