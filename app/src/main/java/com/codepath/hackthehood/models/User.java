package com.codepath.hackthehood.models;

import com.codepath.hackthehood.util.ParseGroupOperator;
import com.codepath.hackthehood.util.ParseIterator;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

/**
 * Created by thomasharte on 12/10/2014.
 *
 * History! This used to be a subclass of ParseUser, claiming the table _User.
 * This created issues with ParseLoginUI. Which maybe we don't care about. Who knows?
 */
public class User {

    /*
        Exposed properties:

            String firstName
            String lastName
            String phoneNumber
            Website website
            ApplicationStatus applicationStatus
            String email
     */

    static public void addDefaultWebsite(final SaveCallback saveCallback) {
        final Website newWebsite = new Website();

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
                                User.setWebsite(newWebsite);
                                User.setApplicationStatus(User.APPSTATUS_STARTED);
                            }

                            if (saveCallback != null)
                                saveCallback.done(e);
                        }
                    });

                }
            }
        });
    }

    public static void prefetchAllFields() {
        ParseGroupOperator.fetchObjectGroupsInBackground(true,
                new ParseIterator() {
                    private ArrayList<ParseObject> websiteChildren = null;
                    private ArrayList<ParseObject> pageResources = null;
                    private ArrayList<ParseObject> pageResourceChildren = null;

                    @Override
                    protected void findNextObject() {

                        if (considerNextObject(ParseUser.getCurrentUser())) return;

                        Website website = User.getWebsite();
                        if (website == null) return;
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

    private final static String fullNameKey = "fullName";
    public static void setFullName(String fullName) {
        ParseUser.getCurrentUser().put(fullNameKey, fullName);
    }
    public static String getFullName() {
        return ParseUser.getCurrentUser().getString(fullNameKey);
    }

    private final static String phoneNumberKey = "phoneNumber";
    public static void setPhoneNumber(String phoneNumber) {
        ParseUser.getCurrentUser().put(phoneNumberKey, phoneNumber);
    }
    public static String getPhoneNumber() {
        return ParseUser.getCurrentUser().getString(phoneNumberKey);
    }

    private static final String websiteKey = "website";
    public static void setWebsite(Website website) {
        ParseUser.getCurrentUser().put(websiteKey, website);
    }
    public static Website getWebsite() {
        return (Website)ParseUser.getCurrentUser().get(websiteKey);
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
    private final static String applicationStatusKey = "applicationStatus";
    public static void setApplicationStatus(int applicationStatus) {
        ParseUser.getCurrentUser().put(applicationStatusKey, applicationStatus);
    }
    public static int getApplicationStatus() {
        int status = ParseUser.getCurrentUser().getInt(applicationStatusKey);
        return (status >= APPSTATUS_STARTED && status <= APPSTATUS_SITE_COMPLETED) ? status : APPSTATUS_ACCEPTED;
    }
}
