package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasharte on 12/10/2014.
 */
@ParseClassName("Website")
public class Website extends ParseObject {

    public Website() {}

    /*
        Exposed properties:

            List<WebsitePage> websitePages
            String businessName
            String typeOfBusiness
            String emailAddress
            Address address
            String phoneNumber
            String onlinePresenceType
            URL facebookUrl
            URL yelpUrl
            URL twitterUrl
            URL instagramUrl
            List<URL> otherUrls
            ImageResource logo
            ImageResource header
     */

    private void addPage(final SaveCallback callback, final ArrayList<WebsitePage> websites) {
        final WebsitePage newPage = new WebsitePage();
        newPage.addDefaultImageResources(
                new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                        if (e != null) {
                           callback.done(e);
                           return;
                        } else {
                            websites.add(newPage);
                            newPage.saveInBackground(callback);
                        }
                        }
                    });
    }

    public void addStandardPagesAndFields(final SaveCallback saveCallback) {
        final ArrayList<WebsitePage> websites = new ArrayList<WebsitePage>();
        final ImageResource logo = new ImageResource();
        final ImageResource header = new ImageResource();

        SaveCallback scaffoldingCallback = new SaveCallback() {

            private int callbacksRemaining = 5;
            private boolean hasPosted = false;

            @Override
            public void done(ParseException e) {
                if(hasPosted) return;

                if(e != null) {
                    hasPosted = true;
                    if(saveCallback != null) {
                        saveCallback.done(e);
                    }
                }

                callbacksRemaining--;
                if(callbacksRemaining == 0) {
                    hasPosted = true;

                    setWebsitePages(websites);
                    setHeader(header);
                    setLogo(logo);

                    if(saveCallback != null)
                        saveCallback.done(null);
                }
            }
        };
        addPage(scaffoldingCallback, websites);
        addPage(scaffoldingCallback, websites);
        addPage(scaffoldingCallback, websites);
        logo.saveInBackground(scaffoldingCallback);
        header.saveInBackground(scaffoldingCallback);
    }

    private final String websitePagesKey = "websitePages";
    public void setWebsitePages(List<WebsitePage> pages) {
        put(websitePagesKey, pages);
    }
    public List<WebsitePage> getWebsitePages() {
        return getList(websitePagesKey);
    }

    private final String businessNameKey = "businessName";
    public void setBusinessName(String businessName) {
        put(businessNameKey, businessName);
    }
    public String getBusinessName() {
        return getString(businessNameKey);
    }

    private final String typeOfBusinessKey = "typeOfBusiness";
    public void setTypeOfBusiness(String typeOfBusiness) {
        put(typeOfBusinessKey, typeOfBusiness);
    }
    public String getTypeOfBusiness() {
        return getString(typeOfBusinessKey);
    }

    private final String emailAddressKey = "emailAddress";
    public void setEmailAddress(String emailAddress) {
        put(emailAddressKey, emailAddress);
    }
    public String getEmailAddress() {
        return getString(emailAddressKey);
    }

    private final String addressKey = "address";
    public void setAddress(Address address) {
        put(addressKey, address);
    }
    public Address getAddress() {
        return (Address)get(addressKey);
    }

    private final String phoneNumberKey = "phoneNumber";
    public void setPhoneNumber(String phoneNumber) {
        put(phoneNumberKey, phoneNumber);
    }
    public String getPhoneNumber() {
        return getString(phoneNumberKey);
    }

    private final String onlinePresenceTypeKey = "onlinePresenceType";
    public void setOnlinePresenceType(String type) {
        put(onlinePresenceTypeKey, type);
    }
    public String getOnlinePresenceType() {
        return getString(onlinePresenceTypeKey);
    }

    private final String facebookUrlKey = "facebookUrl";
    public void setFacebookUrl(String facebookUrl) {
        put(facebookUrlKey, facebookUrl);
    }
    public String getFacebookUrl() {
        return getString(facebookUrlKey);
    }

    private final String yelpUrlKey = "yelpUrl";
    public void setYelpUrl(String yelpUrl) {
        put(yelpUrlKey, yelpUrl);
    }
    public String getYelpUrl() {
        return getString(yelpUrlKey);
    }

    private final String twitterUrlKey = "twitterUrl";
    public void setTwitterUrl(String twitterUrl) {
        put(twitterUrlKey, twitterUrl);
    }
    public String getTwitterUrl() {
        return getString(twitterUrlKey);
    }

    private final String instagramUrlKey = "instagramUrl";
    public void setInstagramUrl(String instagramUrl) {
        put(instagramUrlKey, instagramUrl);
    }
    public String getInstagramUrl() {
        return getString(instagramUrlKey);
    }

    private final String otherUrlsKey = "otherUrls";
    public void setOtherUrls(List<String> otherUrls) {
        put(otherUrlsKey, otherUrls);
    }
    public List<String> getOtherUrls() {
        return (List<String>)get(otherUrlsKey);
    }

    private final String logoKey = "logo";
    public void setLogo(ImageResource logo) {
        put(logoKey, logo);
    }
    public ImageResource getLogo() {
        return (ImageResource)get(logoKey);
    }

    private final String headerKey = "header";
    public void setHeader(ImageResource header) {
        put(headerKey, header);
    }
    public ImageResource getHeader() {
        return (ImageResource)get(headerKey);
    }
}
