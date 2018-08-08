package com.example.goda.meraslidertask.models;

public class UserLocation {

    private double lat;
    private double lng;
    private int userId;
    private String name;


    public UserLocation(double lat, double lng, int userId, String name) {
        this.lat = lat;
        this.lng = lng;
        this.userId = userId;
        this.name=name;
    }

    public UserLocation() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
