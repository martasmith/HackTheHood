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

    static public User testUser() {
        return new User();
    }

}
