package com.codepath.hackthehood.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

/**
 * Created by thomasharte on 12/10/2014.
 */
@ParseClassName("Website")
public class WebsitePage extends ParseObject {

    public WebsitePage() {}

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
    public List<PageResource> getResources() {
        return (List<PageResource>)get(pageResourcesKey);
    }
}
