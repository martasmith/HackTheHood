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

}
