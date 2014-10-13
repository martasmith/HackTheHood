package com.codepath.hackthehood.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;

/**
 * Created by thomasharte on 12/10/2014.
 */
@Table(name="Users")
public class User extends Model {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "website")
    private Website website;

    /**
     * @category factory methods
     */
    static public User getTestUser() {
        final String testUserName = "__TEST__";

        // attempt to pull the existing test-user record;
        // if that succeeds then return it
        User testUser = new Select().from(User.class).where("first_name = '" + testUserName + "'").executeSingle();
        if(testUser != null)
            return testUser;

        // otherwise let's flesh out a new test user
        testUser = new User();
        testUser.setFirstName(testUserName);

        Website testWebsite = new Website();
        testWebsite.addStandardPages();
        testWebsite.save();

        testUser.setWebsite(testWebsite);
        testUser.save();

        return testUser;
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

    public void setWebsite(Website website) {
        this.website = website;
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

    public Website getWebsite() {
        return website;
    }
}
