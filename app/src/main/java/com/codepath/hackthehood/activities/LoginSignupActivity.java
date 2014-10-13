package com.codepath.hackthehood.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import com.codepath.hackthehood.R;
import com.codepath.hackthehood.adapters.LoginSignupPagerAdapter;

public class LoginSignupActivity extends FragmentActivity {

    private LoginSignupPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);

        // establish the login and signup pages within the view pager, via an adaptor, naturally
        viewPager = (ViewPager)findViewById(R.id.pager);
        pagerAdapter = new LoginSignupPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // establish tabs; I don't think this is 2.x compatible; will need to discuss —
        // this pulls the tab count and titles from the pager adaptor and establishes push
        // from the tab bar to the view paging
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}
        };

        for(int i = 0; i < pagerAdapter.getCount(); i++)
            actionBar.addTab(
                    actionBar.newTab()
                        .setText(pagerAdapter.getPageTitle(i))
                        .setTabListener(tabListener)
            );

        // also establish push from the pager to the tab bar
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                }
        );
    }

}
