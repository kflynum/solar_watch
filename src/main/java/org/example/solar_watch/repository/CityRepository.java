package org.example.solar_watch.repository;

import org.example.solar_watch.model.CityGeoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityGeoResponse, Long> {

    Optional<CityGeoResponse> findByName(String name);
}
