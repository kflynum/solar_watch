package org.example.solar_watch.model;




import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private String state;


    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SunriseSunset> sunriseSunsets = new ArrayList<>();

    public String getName() {
        return name;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public String getCountry() {
        return country;
    }
    public String getState() {
        return state;
    }
    public long getId() {
        return id;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setState(String state) {
        this.state = state;
    }
}
