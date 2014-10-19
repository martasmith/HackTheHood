package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("WebsitePage")
public class WebsitePage extends ParseObject {

    public WebsitePage() {}

    private void addImageResource(final SaveCallback saveCallback, final ArrayList<PageResource> pageResources) {
        final PageResource pageResource = new PageResource();
        pageResource.addImageResource(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    pageResources.add(pageResource);
                    pageResource.saveInBackground(saveCallback);
                } else
                    saveCallback.done(e);
            }
        });
    }

    public void addDefaultImageResources(final SaveCallback saveCallback) {
        final ArrayList<PageResource> pageResources = new ArrayList<PageResource>();

        SaveCallback scaffoldingCallback = new SaveCallback() {

            private int callbacksRemaining = 3;
            private boolean hasPosted = false;

            @Override
            public void done(ParseException e) {
                if(hasPosted) return;

                if(e != null) {
                    hasPosted = true;
                    if(saveCallback != null) {
                        saveCallback.done(e);
                    }
                }

                callbacksRemaining--;
                if(callbacksRemaining == 0) {
                    hasPosted = true;

                    setPageResources(pageResources);

                    if(saveCallback != null)
                        saveCallback.done(null);
                }
            }
        };
        addImageResource(saveCallback, new ArrayList<PageResource>());
        addImageResource(saveCallback, new ArrayList<PageResource>());
        addImageResource(saveCallback, new ArrayList<PageResource>());
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
