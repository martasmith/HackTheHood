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

    @Column(name = "street_address")    private String streetAddress;
    @Column(name = "city")              private String city;
    @Column(name = "postal_code")       private String postalCode;
    @Column(name = "latitude")          private Double latitude;
    @Column(name = "longitude")         private Double longitude;

    /**
     * @category setters
     */

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @category getters
     */
    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
