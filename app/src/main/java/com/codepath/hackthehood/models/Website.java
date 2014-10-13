package com.codepath.hackthehood.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasharte on 12/10/2014.
 */
@Table(name="Website")
public class Website extends Model {

    @Column(name = "business_name")
    private String businessName;
    @Column(name = "type_of_business")
    private String typeOfBusiness;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "address")
    private Address address;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "facebook_url")
    private URL facebookUrl;
    @Column(name = "yelp_url")
    private URL yelpUrl;
    @Column(name = "twitter_url")
    private URL twitterUrl;
    @Column(name = "instagram_url")
    private URL instagramUrl;
    @Column(name = "other_urls")
    private ArrayList<URL> otherUrls;
    @Column(name = "logo")
    private ImageResource logo;
    @Column(name = "header")
    private ImageResource header;

    /**
     * @category helpers
     */
    public void addStandardPages () {
        for(int pageNumber = 1; pageNumber <= 3; pageNumber++) {
            WebsitePage newPage = new WebsitePage();
            newPage.setPageNumber(pageNumber);
            newPage.setWebsite(this);
            newPage.save();
        }
    }

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

    public List<WebsitePage> getPages() {
        return getMany(WebsitePage.class, "website");
    }
}
