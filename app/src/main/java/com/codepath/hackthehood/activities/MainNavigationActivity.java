package com.codepath.hackthehood.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.fragments.ApplicationFragment;
import com.codepath.hackthehood.fragments.FragmentNavigationDrawer;

public class MainNavigationActivity extends ActionBarActivity {
    private FragmentNavigationDrawer navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        // Find our drawer view
        navDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
        // Setup drawer view
        navDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer),
                R.layout.drawer_nav_item, R.id.flContent);
        // Add nav items
        navDrawer.addNavItem("My Application", "Application Fragment", ApplicationFragment.class);
        // Select default
        if (savedInstanceState == null) {
            navDrawer.selectDrawerItem(0);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        navDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        navDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }
}
