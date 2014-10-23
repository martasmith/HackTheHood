package com.codepath.hackthehood.models;

import com.parse.ParseClassName;

/**
 * Created by thomasharte on 12/10/2014.
 */

@ParseClassName("Address")
public class Address extends NullForNothingParseObject {

    public Address() {}

    /*
        Exposed properties:

            String streetAddress
            String city
            String postalCode
     */

    private final static String streetAddressKey = "streetAddress";
    public void setStreetAddress(String streetAddress) {
        put(streetAddressKey, streetAddress);
    }
    public String getStreetAddress() {
        return getString(streetAddressKey);
    }

    private final static String cityKey = "city";
    public void setCity(String city) {
        put(cityKey, city);
    }
    public String getCity() {
        return getString(cityKey);
    }

    private final static String postalCodeKey = "postalCode";
    public void setPostalCode(String postalCode) {
        put(postalCodeKey, postalCode);
    }
    public String getPostalCode() {
        return getString(postalCodeKey);
    }

}
