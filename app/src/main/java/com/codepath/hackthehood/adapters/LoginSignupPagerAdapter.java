package com.codepath.hackthehood.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.hackthehood.fragments.LoginFragment;
import com.codepath.hackthehood.fragments.SignupFragment;

/**
 * Created by thomasharte on 13/10/2014.
 */
public class LoginSignupPagerAdapter extends FragmentPagerAdapter {

    public LoginSignupPagerAdapter(android.support.v4.app.FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            default:    return new SignupFragment();
            case 1:     return new LoginFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            default:    return "Sign up";
            case 1:     return "Login";
        }
    }
}
