package org.example.solar_watch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SunriseSunsetResults(
        String sunrise,
        String sunset,
        @JsonProperty("solar_noon") String solarNoon,
        @JsonProperty("day_length") int dayLength,
        @JsonProperty("civil_twilight_begin") String civilTwilightBegin,
        @JsonProperty("civil_twilight_end") String civilTwilightEnd
) {}
