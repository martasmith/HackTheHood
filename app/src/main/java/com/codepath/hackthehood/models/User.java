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
        this.put(firstNameKey, firstName);
    }
    public String getFirstName() {
        return (String)this.get(firstNameKey);
    }

    private final String lastNameKey = "lastName";
    public void setLastName(String lastName) {
        this.put(lastNameKey, lastName);
    }
    public String getLastName() {
        return (String)this.get(lastNameKey);
    }

    private final String phoneNumberKey = "phoneNumber";
    public void setPhoneNumber(String phoneNumber) {
        this.put(phoneNumberKey, phoneNumber);
    }
    public String getPhoneNumber() {
        return (String)this.get(phoneNumberKey);
    }

    private final String websiteKey = "webSite";
    public void setWebsite(Website website) {
        this.put(websiteKey, website);
    }
    public Website getWebsite() {
        return (Website)this.get(websiteKey);
    }

    private final String applicationStatusKey = "applicationStatus";
    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.put(applicationStatusKey, applicationStatus);
    }
    public ApplicationStatus getApplicationStatus() {
        return (ApplicationStatus)this.get(applicationStatusKey);
    }
}
