package com.codepath.hackthehood.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.adapters.LoginSignupPagerAdapter;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class LoginSignupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_signup);

        // disable rotation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // establish the login and signup pages within the view pager, via an adaptor, naturally
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        LoginSignupPagerAdapter pagerAdapter = new LoginSignupPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // establish tabs; I don't think this is 2.x compatible; will need to discuss —
        // this pulls the tab count and titles from the pager adaptor and establishes push
        // from the tab bar to the view paging
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

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

    @Override
    protected void onDestroy() {
        Crouton.cancelAllCroutons();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition (R.anim.open_left, R.anim.close_right);
    }
}
