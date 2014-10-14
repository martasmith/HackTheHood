package com.codepath.hackthehood.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by thomasharte on 12/10/2014.
 */
@Table(name="ImageResources")
public class ImageResource extends Model implements Serializable {

    @Column(name = "url")   private URL url;

    /**
     * @category setters
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * @category getters
     */
    public URL getUrl() {
        return url;
    }
}
