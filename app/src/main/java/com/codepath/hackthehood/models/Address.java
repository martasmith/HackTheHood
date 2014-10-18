package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by thomasharte on 12/10/2014.
 */

@ParseClassName("Address")
public class Address extends ParseObject {

    public Address() {}

    /*
        Exposed properties:

            String streetAddress
            String city
            String postalCode
     */

    private final String streetAddressKey = "streetAddress";
    public void setStreetAddress(String streetAddress) {
        put(streetAddressKey, streetAddress);
    }
    public String getStreetAddress() {
        return getString(streetAddressKey);
    }

    private final String cityKey = "city";
    public void setCity(String city) {
        put(cityKey, city);
    }
    public String getCity() {
        return getString(cityKey);
    }

    private final String postalCodeKey = "postalCode";
    public void setPostalCode(String postalCode) {
        put(postalCodeKey, postalCode);
    }
    public String getPostalCode() {
        return getString(postalCodeKey);
    }

}
