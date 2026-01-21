package org.example.solar_watch.service;

import org.example.solar_watch.model.CityGeoResponse;
import org.example.solar_watch.model.SunriseSunsetResponse;
import org.example.solar_watch.model.SunriseSunsetResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class OpenWeatherServiceTest {

    private RestTemplate restTemplate;
    private OpenWeatherService service;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        service = new OpenWeatherService(restTemplate);
    }

    @Test
    void getCordForCity_shouldReturnFirstItem_whenResponseNotEmpty() {
        CityGeoResponse r = new CityGeoResponse();
        r.setLat(10.5);
        r.setLon(-20.5);

        CityGeoResponse[] arr = new CityGeoResponse[]{r};

        when(restTemplate.getForObject(anyString(), eq(CityGeoResponse[].class))).thenReturn(arr);

        CityGeoResponse result = service.getCordForCity("TestCity");

        assertNotNull(result);
        assertEquals(10.5, result.getLat());
        assertEquals(-20.5, result.getLon());
    }

    @Test
    void getCordForCity_shouldThrow_whenResponseEmpty() {
        when(restTemplate.getForObject(anyString(), eq(CityGeoResponse[].class))).thenReturn(new CityGeoResponse[]{});

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.getCordForCity("NoCity"));
        assertTrue(ex.getMessage().contains("City not found"));
    }

    @Test
    void getSunRiseSet_shouldReturnResults_whenStatusOk() {
        SunriseSunsetResults results = new SunriseSunsetResults();
        results.setSunrise("2020-01-01T07:00:00+00:00");
        results.setSunset("2020-01-01T17:00:00+00:00");

        SunriseSunsetResponse response = new SunriseSunsetResponse();
        response.setStatus("OK");
        response.setResults(results);

        when(restTemplate.getForObject(anyString(), eq(SunriseSunsetResponse.class))).thenReturn(response);

        SunriseSunsetResults r = service.getSunRiseSet(1.0, 2.0, LocalDate.of(2020,1,1));

        assertNotNull(r);
        assertEquals("2020-01-01T07:00:00+00:00", r.getSunrise());
        assertEquals("2020-01-01T17:00:00+00:00", r.getSunset());
    }

    @Test
    void getSunRiseSet_shouldThrow_whenResponseInvalid() {
        when(restTemplate.getForObject(anyString(), eq(SunriseSunsetResponse.class))).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> service.getSunRiseSet(1.0,2.0, LocalDate.now()));

        SunriseSunsetResponse bad = new SunriseSunsetResponse();
        bad.setStatus("ERROR");
        when(restTemplate.getForObject(anyString(), eq(SunriseSunsetResponse.class))).thenReturn(bad);

        assertThrows(IllegalStateException.class, () -> service.getSunRiseSet(1.0,2.0, LocalDate.now()));
    }
}

