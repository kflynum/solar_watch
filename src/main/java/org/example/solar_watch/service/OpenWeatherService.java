package org.example.solar_watch.service;

import org.example.solar_watch.model.CityGeoResponse;
import org.example.solar_watch.model.SunriseSunsetResponse;
import org.example.solar_watch.model.SunriseSunsetResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class OpenWeatherService {
    @Value("${api.weather.key}")
    private String API_KEY;
    private static final Logger logger = LoggerFactory.getLogger(OpenWeatherService.class);

    private final RestTemplate restTemplate;
    public OpenWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CityGeoResponse getCordForCity(String city) {
        String url = String.format("https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s", city, API_KEY);

        CityGeoResponse[] response = restTemplate.getForObject(url, CityGeoResponse[].class);

        if (response == null || response.length == 0) {
            throw new IllegalArgumentException("City not found: " + city);
        }

        logger.info("Geo API raw response: {}", Arrays.toString(response));

        return response[0];
    }

    public SunriseSunsetResults getSunRiseSet(double latitude, double longitude, LocalDate date) {
        String url = String.format("https://api.sunrise-sunset.org/json?lat=%f&lng=%f&date=%s&formatted=0", latitude, longitude, date);
        SunriseSunsetResponse response = restTemplate.getForObject(url, SunriseSunsetResponse.class);
        if (response == null || !"OK".equalsIgnoreCase(response.status())) {
            throw new IllegalStateException("Invalid response from Sunrise-Sunset API");
        }
        logger.info("Sunrise-Sunset API raw response: {}", response);

        return  response.results();
    }
}
