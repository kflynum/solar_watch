package org.example.solar_watch.service;

import org.example.solar_watch.model.City;
import org.example.solar_watch.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }
    public Optional<City> getCityByName(String name) {
        return cityRepository.findByName(name);
    }
    @Transactional
    public City save(City city) {
        return cityRepository.save(city);
    }

    public City create(String name, double lat, double lon, String state, String country) {
        City city = new City();
        city.setName(name);
        city.setLatitude(lat);
        city.setLongitude(lon);
        city.setState(state);
        city.setCountry(country);
        return save(city);

    }
}
