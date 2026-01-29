package org.example.solar_watch.controller;

import org.example.solar_watch.model.*;
import org.example.solar_watch.service.CityService;
import org.example.solar_watch.service.OpenWeatherService;
import org.example.solar_watch.service.SunriseSunsetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
    // CREATE
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/cities/{cityId}/sunrise-sunsets")
    public ResponseEntity<SunriseSunset> create(
            @PathVariable Long cityId,
            @RequestBody SunriseSunsetRequest request) {

        SunriseSunset created = sunriseSunsetService.create(cityId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // READ (all)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/cities/{cityId}/sunrise-sunsets")
    public ResponseEntity<List<SunriseSunset>> getAll(@PathVariable Long cityId) {
        return ResponseEntity.ok(sunriseSunsetService.getAllForCity(cityId));
    }

    // READ (by id)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/cities/{cityId}/sunrise-sunsets/{id}")
    public ResponseEntity<SunriseSunset> getById(
            @PathVariable Long cityId,
            @PathVariable Long id) {

        return ResponseEntity.ok(sunriseSunsetService.getById(cityId, id));
    }

    // UPDATE
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/cities/{cityId}/sunrise-sunsets/{id}")
    public ResponseEntity<SunriseSunset> update(
            @PathVariable Long cityId,
            @PathVariable Long id,
            @RequestBody SunriseSunsetRequest request) {

        return ResponseEntity.ok(
                sunriseSunsetService.update(cityId, id, request)
        );
    }

    // DELETE
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/cities/{cityId}/sunrise-sunsets/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long cityId,
            @PathVariable Long id) {

        sunriseSunsetService.delete(cityId, id);
        return ResponseEntity.noContent().build();
    }
}
