package com.codepath.hackthehood.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.PitchDeckActivity;
import com.codepath.hackthehood.adapters.NavDrawerListAdapter;
import com.codepath.hackthehood.models.NavDrawerItem;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by ravi on 10/22/14.
 */
public class FragmentNavigationDrawer extends DrawerLayout {
    private ActionBarDrawerToggle drawerToggle;
    private ListView lvDrawer;
    private NavDrawerListAdapter drawerAdapter;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private int drawerContainerRes;
    private CharSequence mSelectedItemTitle;

    public FragmentNavigationDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public FragmentNavigationDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FragmentNavigationDrawer(Context context) {
        super(context);
    }

    // setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), R.layout.drawer_list_item, R.id.flContent);
    public void setupDrawerConfiguration(ListView drawerListView, int drawerItemRes, int drawerContainerRes) {
        // Setup navigation items array
        navDrawerItems = new ArrayList<NavDrawerItem>();
        drawerAdapter = new NavDrawerListAdapter(getActivity(), navDrawerItems);
        drawerAdapter.setLogoutClickedListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                getActivity().startActivity(new Intent(getActivity(), PitchDeckActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                getActivity().finish();
            }
        });

        // Set the adapter for the list view
        this.drawerContainerRes = drawerContainerRes;
        // Setup drawer list view and related adapter
        lvDrawer = drawerListView;
        lvDrawer.setAdapter(drawerAdapter);

        // Setup item listener
        lvDrawer.setOnItemClickListener(new FragmentDrawerItemListener());
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = setupDrawerToggle();
        setDrawerListener(drawerToggle);
        // set a custom shadow that overlays the main content when the drawer
        // setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // Setup action buttons
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // addNavItem("First", "First Fragment", FirstFragment.class, arguments)
    public void addNavItem(String navTitle, Class<? extends Fragment> fragmentClass, Bundle bundleArgs) {
        navDrawerItems.add(new NavDrawerItem(navTitle, fragmentClass, bundleArgs));
    }

    /** Swaps fragments in the main content view */
    public void selectDrawerItem(int position) {
//        if(position < 0) {
//            ParseUser.logOut();
//            getActivity().startActivity(new Intent(getActivity(), PitchDeckActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//            getActivity().finish();
//            return;
//        }
        NavDrawerItem navItem = navDrawerItems.get(position);
        selectNavDrawerItem(navItem);
    }

    private void selectNavDrawerItem(NavDrawerItem navItem) {
        // Create a new fragment and specify the planet to show based on
        // position

        if (navItem == null) {
            return;
        }
        Fragment fragment = null;
        try {
            fragment = navItem.getFragmentClass().newInstance();
            Bundle args = navItem.getFragmentArgs();
            if (args != null) {
                fragment.setArguments(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(drawerContainerRes, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        mSelectedItemTitle = navItem.getTitle();
        setTitle(mSelectedItemTitle);
        closeDrawer(lvDrawer);
    }


    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }

    private FragmentActivity getActivity() {
        return (FragmentActivity) getContext();
    }

    private android.support.v7.app.ActionBar getActionBar() {
        return ((ActionBarActivity)getActivity()).getSupportActionBar();
    }

    private void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }

    private class FragmentDrawerItemListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectNavDrawerItem((NavDrawerItem) parent.getItemAtPosition(position));
            lvDrawer.setItemChecked(position, true);
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(getActivity(), /* host Activity */
                this, /* DrawerLayout object */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        )
        {
            public void onDrawerClosed(View view) {
                setTitle(mSelectedItemTitle);
                getActivity().supportInvalidateOptionsMenu(); // call onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                mSelectedItemTitle = getActionBar().getTitle().toString();
                getActivity().supportInvalidateOptionsMenu(); // call onPrepareOptionsMenu()
                setTitle("Hack the Hood");
            }
        };
    }

    public boolean isDrawerOpen() {
        return isDrawerOpen(lvDrawer);
    }


}