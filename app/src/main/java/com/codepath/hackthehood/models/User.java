package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
    /*
        Exposed properties:

            String firstName
            String lastName
            String phoneNumber
            Website website
            ApplicationStatus applicationStatus
            String email
     */

    public void addDefaultWebsite(final SaveCallback saveCallback) {
        final Website newWebsite = new Website();
        final User thisUser = this;

        newWebsite.addStandardPages(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e != null) {

                    if(saveCallback != null)
                        saveCallback.done(e);

                } else {

                    newWebsite.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null)
                                thisUser.setWebsite(newWebsite);

                            if (saveCallback != null)
                                saveCallback.done(e);
                        }
                    });

                }
            }
        });

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

    private final String websiteKey = "website";
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
