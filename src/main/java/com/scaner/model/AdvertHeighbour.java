package com.scaner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AdvertHeighbour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idAdvert;
    private String neighbourDirection;
    private Double longitude;
    private Double latitude;

    public AdvertHeighbour() {
    }

    public String getIdAdvert() {
        return idAdvert;
    }

    public void setIdAdvert(String idAdvert) {
        this.idAdvert = idAdvert;
    }

    public String getNeighbourDirection() {
        return neighbourDirection;
    }

    public void setNeighbourDirection(String neighbourDirection) {
        this.neighbourDirection = neighbourDirection;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
