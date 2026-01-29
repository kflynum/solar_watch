package org.example.solar_watch.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SunriseSunsetRequest {
    private String sunrise;
    private String sunset;
    private LocalDate date;
}

