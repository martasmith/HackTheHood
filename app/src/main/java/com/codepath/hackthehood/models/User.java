package com.codepath.hackthehood.models;

import com.codepath.hackthehood.util.ParseGroupOperator;
import com.codepath.hackthehood.util.ParseIterator;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

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

        newWebsite.addStandardPagesAndFields(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e != null) {

                    if (saveCallback != null)
                        saveCallback.done(e);

                } else {

                    newWebsite.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                thisUser.setWebsite(newWebsite);
                                thisUser.setApplicationStatus(User.APPSTATUS_STARTED);
                            }

                            if (saveCallback != null)
                                saveCallback.done(e);
                        }
                    });

                }
            }
        });
    }

    public void prefetchAllFields() {
        ParseGroupOperator.fetchObjectGroupsInBackground(true,
                new ParseIterator() {
                    private ArrayList<ParseObject> websiteChildren = null;
                    private ArrayList<ParseObject> pageResources = null;
                    private ArrayList<ParseObject> pageResourceChildren = null;

                    @Override
                    protected void findNextObject() {

                        if (considerNextObject(User.this)) return;

                        Website website = User.this.getWebsite();
                        if (considerNextObject(website)) return;

                        if(websiteChildren == null) {
                            websiteChildren = new ArrayList<ParseObject>();
                            websiteChildren.add(website.getAddress());
                            websiteChildren.add(website.getHeader());
                            websiteChildren.add(website.getLogo());

                            for(WebsitePage page : website.getWebsitePages())
                                websiteChildren.add(page);
                        }
                        if(considerNextObjects(websiteChildren)) return;

                        if(pageResources == null) {
                            pageResources = new ArrayList<ParseObject>();
                            for(WebsitePage page : website.getWebsitePages()) {
                                for(PageResource resource : page.getPageResources())
                                    pageResources.add(resource);
                            }
                        }
                        if(considerNextObjects(pageResources)) return;

                        if (pageResourceChildren == null) {
                            pageResourceChildren = new ArrayList<ParseObject>();
                            for (ParseObject parseObject : pageResources) {
                                PageResource pageResource = (PageResource) parseObject;

                                ImageResource imageResource = pageResource.getImageResource();
                                if (imageResource != null) pageResourceChildren.add(imageResource);

                                StringResource stringResource = pageResource.getStringResource();
                                if (stringResource != null)
                                    pageResourceChildren.add(stringResource);
                            }
                        }
                        considerNextObjects(pageResourceChildren);
                    }

                }, null);

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

    public final static int APPSTATUS_STARTED = 0;  // leave this as 0; this means users default to
                                                    // APPSTATUS_STARTED without any scaffolding,
                                                    // conveniently if we're using the Parse UI for
                                                    // user creation
    public final static int APPSTATUS_PENDING_REVIEW = 1;
    public final static int APPSTATUS_ACCEPTED = 2;
    public final static int APPSTATUS_DECLINED = 3;
    public final static int APPSTATUS_ASSETS_SUBMITTED = 4;
    public final static int APPSTATUS_SITE_COMPLETED = 5;
    private final String applicationStatusKey = "applicationStatus";
    public void setApplicationStatus(int applicationStatus) {
        put(applicationStatusKey, applicationStatus);
    }
    public int getApplicationStatus() {
        int status = getInt(applicationStatusKey);
        return (status >= APPSTATUS_STARTED && status <= APPSTATUS_SITE_COMPLETED) ? status : APPSTATUS_ACCEPTED;
    }
}
