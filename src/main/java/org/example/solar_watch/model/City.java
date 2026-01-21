package org.example.solar_watch.model;

import jakarta.persistence.Entity;

@Entity
public class City {
    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private String state;

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public String getCountry() {
        return country;
    }
    public String getState() {
        return state;
    }
}
