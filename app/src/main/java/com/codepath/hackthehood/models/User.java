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

    private final String fullNameKey = "fullName";
    public void setFullName(String fullName) {
        put(fullNameKey, fullName);
    }
    public String getFullName() {
        return getString(fullNameKey);
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

    public final static int APPSTATUS_PENDING_REVIEW = 0;
    public final static int APPSTATUS_ACCEPTED = 1;
    public final static int APPSTATUS_DECLINED = 2;
    public final static int APPSTATUS_SITE_COMPLETED = 3;
    private final String applicationStatusKey = "applicationStatus";
    public void setApplicationStatus(int applicationStatus) {
        put(applicationStatusKey, applicationStatus);
    }
    public int getApplicationStatus() {
        return getInt(applicationStatusKey);
    }
}
