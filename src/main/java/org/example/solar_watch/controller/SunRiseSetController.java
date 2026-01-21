package org.example.solar_watch.controller;

import org.example.solar_watch.model.*;
import org.example.solar_watch.service.CityService;
import org.example.solar_watch.service.OpenWeatherService;
import org.example.solar_watch.service.SunriseSunsetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class SunRiseSetController {


    private final OpenWeatherService openWeatherService;
    private final CityService cityService;
    private final SunriseSunsetService sunriseSunsetService;

    public SunRiseSetController(OpenWeatherService openWeatherService, CityService cityService, SunriseSunsetService sunriseSunsetService) {
        this.openWeatherService = openWeatherService;
        this.cityService = cityService;
        this.sunriseSunsetService = sunriseSunsetService;
    }


    @GetMapping("/sunrise-sunset")
    public SunriseSunset getSunRiseSet(@RequestParam String city,@RequestParam LocalDate date) {
        Optional<City> cityObj = cityService.getCityByName(city);
        City citytmp;
        if(cityObj.isEmpty()) {
            CityGeoResponse location = openWeatherService.getCordForCity(city);
            citytmp = cityService.create(city, location.lat(), location.lon(), location.state(), location.country());
            cityService.save(citytmp);
        }
        else {
            citytmp = cityObj.get();
        }
        SunriseSunsetResults sunRiseSet;
        SunriseSunset suntmp;
        if(sunriseSunsetService.getByCityAndDate(citytmp.getId(), date).isEmpty()){
            sunRiseSet = openWeatherService.getSunRiseSet(citytmp.getLatitude(), citytmp.getLongitude(), date);
            suntmp = sunriseSunsetService.create(citytmp, date, sunRiseSet.sunrise(), sunRiseSet.sunset());
            sunriseSunsetService.save(suntmp);
        }
        else {
            suntmp = sunriseSunsetService.getByCityAndDate(citytmp.getId(), date).get();
        }
        return suntmp;
    }
}
