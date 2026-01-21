package org.example.solar_watch.service;

import org.example.solar_watch.model.City;
import org.example.solar_watch.model.SunriseSunset;
import org.example.solar_watch.repository.SunriseSunsetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SunriseSunsetService {
    private final SunriseSunsetRepository sunriseSunsetRepository;

    public SunriseSunsetService(SunriseSunsetRepository sunriseSunsetRepository) {
        this.sunriseSunsetRepository = sunriseSunsetRepository;
    }

    public List<SunriseSunset> getByCityName(Long cityId) {
        return sunriseSunsetRepository.findByCity(cityId);
    }
    public Optional<SunriseSunset> getByCityAndDate(Long cityId, LocalDate date) {
        return sunriseSunsetRepository.findByCityIdAndDate(cityId, date);
    }
    @Transactional
    public SunriseSunset save(SunriseSunset sunriseSunset) {
        return sunriseSunsetRepository.save(sunriseSunset);
    }
    public SunriseSunset create(City city, LocalDate date, String sunrise, String sunset) {
        SunriseSunset sunriseSunset = new SunriseSunset();
        sunriseSunset.setCity(city);
        sunriseSunset.setDate(date);
        sunriseSunset.setSunrise(sunrise);
        sunriseSunset.setSunset(sunset);
        return sunriseSunset;
    }

}
