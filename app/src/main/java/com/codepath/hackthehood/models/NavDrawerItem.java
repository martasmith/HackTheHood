package com.codepath.hackthehood.models;

/**
 * Created by ravi on 10/25/14.
 */
public class NavDrawerItem {
    private String mTitle;

    public NavDrawerItem(){}

    public NavDrawerItem(String title){
        this.mTitle = title;
    }

    public String getTitle(){
        return this.mTitle;
    }

    public void setTitle(String title){
        this.mTitle = title;
    }
}