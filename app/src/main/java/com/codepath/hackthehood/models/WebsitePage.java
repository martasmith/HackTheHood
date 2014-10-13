package com.codepath.hackthehood.models;

import java.util.ArrayList;

/**
 * Created by thomasharte on 12/10/2014.
 */
public class WebsitePage {
    private Integer pageNumber;
    private String title;
    private String text;
    private String notes;
    private ArrayList<PageResource> resources;

    /**
     * @category setters
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setResources(ArrayList<PageResource> resources) {
        this.resources = resources;
    }

    /**
     * @category getters
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getNotes() {
        return notes;
    }

    public ArrayList<PageResource> getResources() {
        return resources;
    }
}
