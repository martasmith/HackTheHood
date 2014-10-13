package com.codepath.hackthehood.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by thomasharte on 12/10/2014.
 */
@Table(name="PageResources")
public class PageResource extends Model {
    @Column(name = "name")
    private String name;
    @Column(name = "text")
    private StringResource text;
    @Column(name = "image")
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
