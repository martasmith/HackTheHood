package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.io.Serializable;

/**
 * Created by thomasharte on 12/10/2014.
 */
@ParseClassName("_User")
public class User extends ParseUser {

    public enum ApplicationStatus {
        PENDING_REVIEW, ACCEPTED, DECLINED, SITE_COMPLETED
    }

    public User () {
    }

    public void addDefaultWebsite() {
        Website newWebsite = new Website();
        newWebsite.addStandardPages();
        this.setWebsite(newWebsite);
    }

    private final String firstNameKey = "firstName";
    public void setFirstName(String firstName) {
        put(firstNameKey, firstName);
    }
    public String getFirstName() {
        return getString(firstNameKey);
    }

    private final String lastNameKey = "lastName";
    public void setLastName(String lastName) {
        put(lastNameKey, lastName);
    }
    public String getLastName() {
        return getString(lastNameKey);
    }

    private final String phoneNumberKey = "phoneNumber";
    public void setPhoneNumber(String phoneNumber) {
        put(phoneNumberKey, phoneNumber);
    }
    public String getPhoneNumber() {
        return getString(phoneNumberKey);
    }

    private final String websiteKey = "webSite";
    public void setWebsite(Website website) {
        put(websiteKey, website);
    }
    public Website getWebsite() {
        return (Website)this.get(websiteKey);
    }

    private final String applicationStatusKey = "applicationStatus";
    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        put(applicationStatusKey, applicationStatus);
    }
    public ApplicationStatus getApplicationStatus() {
        return (ApplicationStatus)get(applicationStatusKey);
    }
}
