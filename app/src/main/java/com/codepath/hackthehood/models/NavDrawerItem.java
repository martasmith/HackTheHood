package com.codepath.hackthehood.models;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by ravi on 10/25/14.
 */
public class NavDrawerItem {
    private final Class<? extends Fragment> fragmentClass;
    private final String title;
    private final Bundle fragmentArgs;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    private Boolean selected;

    public NavDrawerItem(String title, Class<? extends Fragment> fragmentClass) {
        this(title, fragmentClass, null);
    }

    public NavDrawerItem(String title, Class<? extends Fragment> fragmentClass, Bundle args) {
        this.selected = false;
        this.fragmentClass = fragmentClass;
        this.fragmentArgs = args;
        this.title = title;
    }

    public Class<? extends Fragment> getFragmentClass() {
        return fragmentClass;
    }

    public String getTitle() {
        return title;
    }

    public Bundle getFragmentArgs() {
        return fragmentArgs;
    }
}