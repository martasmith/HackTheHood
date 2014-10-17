package com.codepath.hackthehood.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.codepath.hackthehood.R;
import com.codepath.hackthehood.adapters.LoginSignupPagerAdapter;
import com.codepath.hackthehood.models.User;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class LoginSignupActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = (User)ParseUser.getCurrentUser();
        if(user != null) {
            startActivity(new Intent(LoginSignupActivity.this, BusinessFormActivity.class));
            return;
        }

        setContentView(R.layout.activity_login_signup);

        // establish the login and signup pages within the view pager, via an adaptor, naturally
        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        LoginSignupPagerAdapter pagerAdapter = new LoginSignupPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // establish tabs; I don't think this is 2.x compatible; will need to discuss —
        // this pulls the tab count and titles from the pager adaptor and establishes push
        // from the tab bar to the view paging
        final ActionBar actionBar = getActionBar();
        if(actionBar == null) return;
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
