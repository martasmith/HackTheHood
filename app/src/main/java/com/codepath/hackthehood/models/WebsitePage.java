package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasharte on 12/10/2014.
 */
@ParseClassName("WebsitePage")
public class WebsitePage extends ParseObject {

    public WebsitePage() {}

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
            ImageResource imageResource = pageResource.getImage();
            if(imageResource != null)
                imageResources.add(imageResource);
        }
        return imageResources;
    }
}
