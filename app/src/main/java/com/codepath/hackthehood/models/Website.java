package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.net.MalformedURLException;
import java.net.URL;
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
            URL facebookUrl
            URL yelpUrl
            URL twitterUrl
            URL instagramUrl
            List<URL> otherUrls
            ImageResource logo
            ImageResource header
     */

    private void addPage(final int pageNumber, final SaveCallback finalCallback, final ArrayList<WebsitePage> websites) {
        if(pageNumber == 4) {

            setWebsitePages(websites);
            if(finalCallback != null)
                finalCallback.done(null);

        } else {

            WebsitePage newPage = new WebsitePage();
            newPage.setPageNumber(pageNumber);
            websites.add(newPage);

            newPage.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        if(finalCallback != null)
                            finalCallback.done(e);
                    } else {
                        addPage(pageNumber+1, finalCallback, websites);
                    }
                }
            });

        }
    }

    public void addStandardPages(SaveCallback saveCallback) {
        addPage(1, saveCallback, new ArrayList<WebsitePage>());
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

    private final String facebookUrlKey = "facebookUrl";
    public void setFacebookUrl(URL facebookUrl) {
        put(facebookUrlKey, facebookUrl.toString());
    }
    public URL getFacebookUrl() {
        return getURL(facebookUrlKey);
    }

    private final String yelpUrlKey = "yelpUrl";
    public void setYelpUrl(URL yelpUrl) {
        put(yelpUrlKey, yelpUrl.toString());
    }
    public URL getYelpUrl() {
        return getURL(yelpUrlKey);
    }

    private final String twitterUrlKey = "twitterUrl";
    public void setTwitterUrl(URL twitterUrl) {
        put(twitterUrlKey, twitterUrl.toString());
    }
    public URL getTwitterUrl() {
        return getURL(twitterUrlKey);
    }

    private final String instagramUrlKey = "instagramUrl";
    public void setInstagramUrl(URL instagramUrl) {
        put(instagramUrlKey, instagramUrl.toString());
    }
    public URL getInstagramUrl() {
        return getURL(instagramUrlKey);
    }

    private final String otherUrlsKey = "otherUrls";
    public void setOtherUrls(List<URL> otherUrls) {
        ArrayList<String> stringUrls = new ArrayList<String>();
        for(URL url : otherUrls) {
            stringUrls.add(otherUrls.toString());
        }
        put(otherUrlsKey, stringUrls);
    }
    public List<URL> getOtherUrls() {
        List<String> stringUrls = (List<String>)get(otherUrlsKey);
        ArrayList<URL> otherUrls = new ArrayList<URL>();
        for(String stringUrl : stringUrls) {
            try {
                URL url = new URL(stringUrl);
                otherUrls.add(url);
            } catch (MalformedURLException e) {

            }
        }
        return otherUrls;
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

    private URL getURL(String key) {
        try {
            return new URL(getString(key));
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
