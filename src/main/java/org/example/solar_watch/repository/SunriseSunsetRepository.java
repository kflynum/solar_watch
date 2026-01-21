package org.example.solar_watch.repository;

import org.example.solar_watch.model.City;
import org.example.solar_watch.model.SunriseSunset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SunriseSunsetRepository extends JpaRepository {
    List<SunriseSunset> findByCity(Long cityId);
    Optional<SunriseSunset> findByCityIdAndDate(Long cityId, LocalDate date);
}
