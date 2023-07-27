package com.github.sahedw.backend.googlemaps;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class PositionTest {

    @Test
    void testPositionConstructor() {
        // GIVEN
        String latitude = "53.56147";
        String longitude = "9.91507";
        Position position = new Position(latitude, longitude);

        // THEN
        assertThat(position.getLatitude()).isEqualTo(latitude);
        assertThat(position.getLongitude()).isEqualTo(longitude);
    }

}