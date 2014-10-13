package com.codepath.hackthehood.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomasharte on 12/10/2014.
 */
@Table(name="WebsitePages")
public class WebsitePage extends Model {

    @Column(name = "page_number")
    private Integer pageNumber;
    @Column(name = "title")
    private String title;
    @Column(name = "text")
    private String text;
    @Column(name = "notes")
    private String notes;
    @Column(name = "website")
    private Website website;

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

    public void setWebsite(Website website) {
        this.website = website;
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

    public Website getWebsite() {
        return website;
    }

    public List<PageResource> getResources() {
        return getMany(PageResource.class, "website_page");
    }
}
