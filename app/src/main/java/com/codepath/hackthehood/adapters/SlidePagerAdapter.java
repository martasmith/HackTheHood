package com.codepath.hackthehood.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.hackthehood.fragments.GetBusinessOnlineSlideFragment;
import com.codepath.hackthehood.fragments.ImageSlideFragment;
import com.codepath.hackthehood.fragments.WebsiteTemplatesSlideFragment;

/**
 * Created by ravi on 10/12/14.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 3;

    public SlidePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // Support local youth development
                return ImageSlideFragment.newInstance("", "Support local youth development", "Hack the Hood introduces low-income youth of color to tech careers by hiring and training them as web consultants who build and promote sites for small local businesses.");

            case 1:
                return WebsiteTemplatesSlideFragment.newInstance();

            case 2:
                return new GetBusinessOnlineSlideFragment();

            default:
                return null;
        }
    }
}
