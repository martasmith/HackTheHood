package com.codepath.hackthehood.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.fragments.ApplicationFragment;
import com.codepath.hackthehood.fragments.FragmentNavigationDrawer;
import com.codepath.hackthehood.fragments.info.WebviewFragment;

public class MainNavigationActivity extends ActionBarActivity implements NetworkFragmentContainer {
    private FragmentNavigationDrawer navDrawer;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
        if (activityCount > 0) pbLoading.setVisibility(View.VISIBLE);

        // Find our drawer view
        navDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
        // Setup drawer view
        navDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer),
                R.layout.drawer_nav_item, R.id.flContent);
        // Add nav items
        navDrawer.addNavItem("My Application", ApplicationFragment.class, null);

        Bundle webViewFragmentArguments = new Bundle();
        webViewFragmentArguments.putString(WebviewFragment.PAGE_TITLE, "About Hack the Hood");
        webViewFragmentArguments.putString(WebviewFragment.PAGE_URL, "http://www.hackthehood.org/what-we-do.html");
        navDrawer.addNavItem("About", WebviewFragment.class, webViewFragmentArguments);
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

    private int activityCount = 0;

    @Override
    public void incrementActivityCount() {
        if (activityCount == 0 && (pbLoading != null)) {
            pbLoading.setVisibility(View.VISIBLE);
        }
        activityCount++;
    }

    @Override
    public void decrementActivityCount() {
        activityCount--;
        if (activityCount == 0 && (pbLoading != null)) {
            pbLoading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void didReceiveException(Exception e) {
        // This is imaginative, I know
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (navDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
