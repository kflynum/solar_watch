package org.example.solar_watch.controller;

import org.example.solar_watch.model.CityGeoResponse;
import org.example.solar_watch.model.SunriseSunsetResults;
import org.example.solar_watch.service.OpenWeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SunRiseSetControllerTest {

    private OpenWeatherService service;
    private SunRiseSetController controller;

//    @BeforeEach
//    void setUp() {
//        service = mock(OpenWeatherService.class);
//        controller = new SunRiseSetController(service);
//    }
//
//    @Test
//    void getSunRiseSet_shouldReturnFormattedString() {
//        CityGeoResponse geo = new CityGeoResponse(12.34, 56.78, null, null, null);
//
//        SunriseSunsetResults results = new SunriseSunsetResults("2020-01-01T07:00:00+00:00", "2020-01-01T17:00:00+00:00", null,0,null,null);
//
//        when(service.getCordForCity(eq("TestCity"))).thenReturn(geo);
//        when(service.getSunRiseSet(eq(12.34), eq(56.78), any(LocalDate.class))).thenReturn(results);
//
//        String resp = controller.getSunRiseSet("TestCity", LocalDate.of(2020,1,1));
//
//        assertEquals("In TestCity on 2020-01-01, the sun rises at 2020-01-01T07:00:00+00:00 and sets at 2020-01-01T17:00:00+00:00", resp);
//    }
}

