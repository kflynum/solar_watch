package org.example.solar_watch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SunriseSunsetResponse {

    private SunriseSunsetResults results;
    private String status;

    public SunriseSunsetResults getResults() {
        return results;
    }

    public void setResults(SunriseSunsetResults results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}