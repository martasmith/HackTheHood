package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by thomasharte on 12/10/2014.
 */
@ParseClassName("PageResource")
public class PageResource extends ParseObject {

    public PageResource() {}

    public void addImageResource(final SaveCallback saveCallback) {
        final ImageResource imageResource = new ImageResource();
        imageResource.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null) {
                    setImageResource(imageResource);
                }
                if(saveCallback != null)
                    saveCallback.done(e);
            }
        });
    }

    /*
        Exposed properties:

            String name
            StringResource text
            ImageResource image
     */

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
    public void setImageResource(ImageResource image) {
        put(imageKey, image);
    }
    public ImageResource getImageResource() {
        return (ImageResource)get(imageKey);
    }
}
