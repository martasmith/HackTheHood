package com.codepath.hackthehood.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;

/**
 * Created by thomasharte on 12/10/2014.
 */
@ParseClassName("PageResource")
public class PageResource extends ParseObject {

    public PageResource() {}

    private final String nameKey = "name";
    public void setName(String name) {
        put(nameKey, name);
    }
    public String getName() {
        return getString(nameKey);
    }

    private final String textKey = "text";
    public void setText(StringResource text) {
        put(textKey, text);
    }
    public StringResource getText() {
        return (StringResource)get(textKey);
    }

    private final String imageKey = "image";
    public void setImage(ImageResource image) {
        put(imageKey, image);
    }
    public ImageResource getImage() {
        return (ImageResource)get(imageKey);
    }
}
