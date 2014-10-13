package com.codepath.hackthehood.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by thomasharte on 12/10/2014.
 */

@Table(name="Addresses")
public class Address extends Model implements Serializable {
    @Column(name = "full_address")
    private String fullAddress;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
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
