package org.example.solar_watch.controller;

import org.example.solar_watch.model.CityGeoResponse;
import org.example.solar_watch.service.OpenWeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class SunRiseSetController {


    private final OpenWeatherService openWeatherService;

    public SunRiseSetController(OpenWeatherService openWeatherService) {
        this.openWeatherService = openWeatherService;
    }


    @GetMapping("/sunrise-sunset")
    public String getSunRiseSet(@RequestParam String city,@RequestParam LocalDate date) {
        CityGeoResponse location = openWeatherService.getCordForCity(city);
        var sunRiseSet = openWeatherService.getSunRiseSet(location.getLat(), location.getLon(), date);
        return String.format("In %s on %s, the sun rises at %s and sets at %s",
                city,
                date,
                sunRiseSet.getSunrise(),
                sunRiseSet.getSunset());
    }
}
