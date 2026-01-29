package org.example.solar_watch.controller;


import lombok.RequiredArgsConstructor;
import org.example.solar_watch.model.City;
import org.example.solar_watch.model.CityRequest;
import org.example.solar_watch.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    // CREATE
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody CityRequest request) {
        City createdCity = cityService.createCity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    // READ (all)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    // READ (by id)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    // UPDATE
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(
            @PathVariable long id,
            @RequestBody CityRequest request) {

        return ResponseEntity.ok(cityService.updateCity(id, request));
    }

    // DELETE
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}

