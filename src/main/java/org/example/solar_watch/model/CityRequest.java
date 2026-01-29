package org.example.solar_watch.model;

import lombok.Data;

@Data
public class CityRequest {
    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private String state;
}
