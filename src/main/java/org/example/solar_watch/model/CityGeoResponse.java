package org.example.solar_watch.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CityGeoResponse(
        double lat,
        double lon,
        String name,
        String country,
        String state
) {}
