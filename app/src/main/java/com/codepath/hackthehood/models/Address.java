package com.codepath.hackthehood.models;

/**
 * Created by thomasharte on 12/10/2014.
 */
public class Address {
    private String fullAddress;
    private Double latitude;
    private Double longitude;

    /**
     * @category setters
     */
    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * @category getters
     */
    public String getFullAddress() {
        return fullAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
