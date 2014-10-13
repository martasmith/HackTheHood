package com.codepath.hackthehood.models;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by thomasharte on 12/10/2014.
 */
public class Website {

    private String businessName;
    private String typeOfBusiness;
    private String emailAddress;
    private Address address;
    private String phoneNumber;
    private URL facebookUrl;
    private URL yelpUrl;
    private URL twitterUrl;
    private URL instagramUrl;
    private ArrayList<URL> otherUrls;
    private ImageResource logo;
    private ImageResource header;
    private ArrayList<WebsitePage> pages;

    /**
     * @category setters
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setTypeOfBusiness(String typeOfBusiness) {
        this.typeOfBusiness = typeOfBusiness;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFacebookUrl(URL facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public void setYelpUrl(URL yelpUrl) {
        this.yelpUrl = yelpUrl;
    }

    public void setTwitterUrl(URL twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public void setInstagramUrl(URL instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public void setOtherUrls(ArrayList<URL> otherUrls) {
        this.otherUrls = otherUrls;
    }

    public void setLogo(ImageResource logo) {
        this.logo = logo;
    }

    public void setHeader(ImageResource header) {
        this.header = header;
    }

    public void setPages(ArrayList<WebsitePage> pages) {
        this.pages = pages;
    }

    /**
     * @category getters
     */
    public String getBusinessName() {
        return businessName;
    }

    public String getTypeOfBusiness() {
        return typeOfBusiness;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public URL getFacebookUrl() {
        return facebookUrl;
    }

    public URL getYelpUrl() {
        return yelpUrl;
    }

    public URL getTwitterUrl() {
        return twitterUrl;
    }

    public URL getInstagramUrl() {
        return instagramUrl;
    }

    public ArrayList<URL> getOtherUrls() {
        return otherUrls;
    }

    public ImageResource getLogo() {
        return logo;
    }

    public ImageResource getHeader() {
        return header;
    }

    public ArrayList<WebsitePage> getPages() {
        return pages;
    }
}
