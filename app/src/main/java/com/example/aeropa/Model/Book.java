package com.example.aeropa.Model;

import java.io.Serializable;

public class Book implements Serializable {
//    private String bookingID;
//    private int flightCode;
//    private String userId;
//    private int numPassenger;
//    private String seats;

    private String bookingID;
    private int flightCode;
    private String userId;
    private int numPassenger;
    private String seats;


    public Book() {
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public int getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(int flightCode) {
        this.flightCode = flightCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNumPassenger() {
        return numPassenger;
    }

    public void setNumPassenger(int numPassenger) {
        this.numPassenger = numPassenger;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}
