package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasharte on 12/10/2014.
 */
@ParseClassName("WebsitePage")
public class WebsitePage extends ParseObject {

    public WebsitePage() {}

    private void addImageResource(final int count, final SaveCallback saveCallback, final ArrayList<PageResource> pageResources) {
        if(count == 0) {
            setPageResources(pageResources);

            if(saveCallback != null)
                saveCallback.done(null);
            return;
        }

        final PageResource pageResource = new PageResource();
        pageResource.addImageResource(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    if(saveCallback != null)
                        saveCallback.done(e);
                } else {
                    pageResources.add(pageResource);
                    addImageResource(count-1, saveCallback, pageResources);
                }
            }
        });
    }
    public void addDefaultImageResources(SaveCallback saveCallback) {
        addImageResource(3, saveCallback, new ArrayList<PageResource>());
    }

    /*
        Exposed properties:

            Integer pageNumber
            String title
            String text
            String notes
            List<PageResource> pageResources
     */

    private final String pageNumberKey = "pageNumber";
    public void setPageNumber(Integer pageNumber) {
        put(pageNumberKey, pageNumber);
    }
    public Integer getPageNumber() {
        return getInt(pageNumberKey);
    }

    private final String titleKey = "title";
    public void setTitle(String title) {
        put(titleKey, title);
    }
    public String getTitle() {
        return getString(titleKey);
    }

    private final String textKey = "text";
    public void setText(String text) {
        put(textKey, text);
    }
    public String getText() {
        return getString(textKey);
    }

    private final String notesKey = "notes";
    public void setNotes(String notes) {
        put(notesKey, notes);
    }
    public String getNotes() {
        return getString(notesKey);
    }

    private final String pageResourcesKey = "pageResources";
    public void setPageResources(List<PageResource> resources) {
        put(pageResourcesKey, resources);
    }
    public List<PageResource> getPageResources() {
        return (List<PageResource>)get(pageResourcesKey);
    }

    public List<ImageResource> getImageResources() {
        List<PageResource> pageResources = getPageResources();
        ArrayList<ImageResource> imageResources = new ArrayList<ImageResource>();
        for(PageResource pageResource : pageResources) {
            ImageResource imageResource = pageResource.getImageResource();
            if(imageResource != null)
                imageResources.add(imageResource);
        }
        return imageResources;
    }
}
