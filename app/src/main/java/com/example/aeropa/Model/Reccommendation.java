package com.example.aeropa.Model;

import java.io.Serializable;
import java.util.List;

public class Reccommendation implements Serializable {
    private String cityName;
    private String cityCountry;
    private String cityPhoto;
    private List<Flight> flights;

    public Reccommendation() {
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityPhoto() {
        return cityPhoto;
    }

    public void setCityPhoto(String cityPhoto) {
        this.cityPhoto = cityPhoto;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public void setCityCountry(String cityCountry) {
        this.cityCountry = cityCountry;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public double getLowestPrice() {
        if (flights == null || flights.isEmpty()) {
            return -1;
        }
        double lowestPrice = flights.get(0).getPrice();
        for (Flight flight : flights) {
            if (flight.getPrice() < lowestPrice) {
                lowestPrice = flight.getPrice();
            }
        }
        return lowestPrice;
    }
}
