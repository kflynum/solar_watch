package org.example.solar_watch.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityResponse {
    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private String state;
}