package com.example.aeropa.Model;

import java.util.ArrayList;

public class User {
    private String userId;
    private String UserName;
    private String email;
    private String password;
    private String phone;
    private ArrayList<Book> bookingList;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Book> getBookingList() {
        return bookingList;
    }

    public void setBookingList(ArrayList<Book> bookingList) {
        this.bookingList = bookingList;
    }
}
