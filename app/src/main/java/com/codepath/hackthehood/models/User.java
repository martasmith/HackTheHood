package com.codepath.hackthehood.models;

import java.util.ArrayList;

/**
 * Created by thomasharte on 12/10/2014.
 */
public class User {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private ArrayList<Website> websites;

    /**
     * @category factory methods
     */
    static public User testUser() {
        return new User();
    }

    /**
     * @category setters
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWebsites(ArrayList<Website> websites) {
        this.websites = websites;
    }

    /**
     * @category getters
     */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<Website> getWebsites() {
        return websites;
    }
}
