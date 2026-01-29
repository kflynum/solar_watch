package org.example.solar_watch.service;

import org.example.solar_watch.model.City;
import org.example.solar_watch.model.SunriseSunset;
import org.example.solar_watch.model.SunriseSunsetRequest;
import org.example.solar_watch.repository.CityRepository;
import org.example.solar_watch.repository.SunriseSunsetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SunriseSunsetService {
    private final SunriseSunsetRepository sunriseSunsetRepository;
    private final CityRepository cityRepository;

    public SunriseSunsetService(SunriseSunsetRepository sunriseSunsetRepository, CityRepository cityRepository) {
        this.cityRepository = cityRepository;
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
    // CREATE
    public SunriseSunset create(Long cityId, SunriseSunsetRequest request) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new RuntimeException("City not found with id " + cityId));

        SunriseSunset ss = new SunriseSunset();
        ss.setSunrise(request.getSunrise());
        ss.setSunset(request.getSunset());
        ss.setDate(request.getDate());
        ss.setCity(city);

        return sunriseSunsetRepository.save(ss);
    }

    // READ (all for city)
    public List<SunriseSunset> getAllForCity(Long cityId) {
        return sunriseSunsetRepository.findByCityId(cityId);
    }

    // READ (by id)
    public SunriseSunset getById(Long cityId, Long id) {
        return sunriseSunsetRepository.findByIdAndCityId(id, cityId)
                .orElseThrow(() -> new RuntimeException("SunriseSunset not found"));
    }

    // UPDATE
    public SunriseSunset update(Long cityId, Long id, SunriseSunsetRequest request) {
        SunriseSunset ss = getById(cityId, id);

        ss.setSunrise(request.getSunrise());
        ss.setSunset(request.getSunset());
        ss.setDate(request.getDate());

        return sunriseSunsetRepository.save(ss);
    }

    // DELETE
    public void delete(Long cityId, Long id) {
        SunriseSunset ss = getById(cityId, id);
        sunriseSunsetRepository.delete(ss);
    }

}
