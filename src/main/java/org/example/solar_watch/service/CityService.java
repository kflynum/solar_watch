package org.example.solar_watch.service;

import org.example.solar_watch.model.City;
import org.example.solar_watch.model.CityRequest;
import org.example.solar_watch.repository.CityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    // CREATE
    public City createCity(CityRequest request) {
        City city = new City();
        city.setName(request.getName());
        city.setLatitude(request.getLatitude());
        city.setLongitude(request.getLongitude());
        city.setCountry(request.getCountry());
        city.setState(request.getState());

        return cityRepository.save(city);
    }

    // READ (all)
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // READ (by id)
    public City getCityById(long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("City not found with id " + id));
    }

    // UPDATE
    public City updateCity(long id, CityRequest request) {
        City city = getCityById(id);

        city.setName(request.getName());
        city.setLatitude(request.getLatitude());
        city.setLongitude(request.getLongitude());
        city.setCountry(request.getCountry());
        city.setState(request.getState());

        return cityRepository.save(city);
    }

    // DELETE
    public void deleteCity(long id) {
        City city = getCityById(id);
        cityRepository.delete(city);
    }
}
