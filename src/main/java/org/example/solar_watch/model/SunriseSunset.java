package org.example.solar_watch.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "sunrise_sunset")
public class SunriseSunset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sunrise;
    private String sunset;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

}
