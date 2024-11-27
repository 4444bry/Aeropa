package com.example.aeropa.Model;public class Location {
    private String Id;
    private String Name;

    public Location() {
    }

    @Override
    public String toString() {
        return Name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
