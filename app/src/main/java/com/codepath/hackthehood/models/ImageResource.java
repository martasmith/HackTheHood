package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by thomasharte on 12/10/2014.
 */
@ParseClassName("ImageResource")
public class ImageResource extends ParseObject {

    public ImageResource() {}

    /*
        Exposed properties:

            URL url
     */

    private final String urlKey = "Url";
    public void setUrl(URL url) {
        put(urlKey, url.toString());
    }
    public URL getUrl() {
        try {
            return new URL(getString(urlKey));
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
