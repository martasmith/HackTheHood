package com.codepath.hackthehood.models;

/**
 * Created by thomasharte on 12/10/2014.
 */
public class PageResource {
    private String name;
    private StringResource text;
    private ImageResource image;

    /**
     * @category setters
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setText(StringResource text) {
        this.text = text;
    }

    public void setImage(ImageResource image) {
        this.image = image;
    }

    /**
     * @category getters
     */
    public String getName() {
        return name;
    }

    public StringResource getText() {
        return text;
    }

    public ImageResource getImage() {
        return image;
    }
}
