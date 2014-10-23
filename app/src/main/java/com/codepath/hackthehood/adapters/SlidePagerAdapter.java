package com.codepath.hackthehood.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.hackthehood.fragments.ImageSlideFragment;
import com.codepath.hackthehood.fragments.WebsiteTemplatesSlideFragment;

/**
 * Created by ravi on 10/12/14.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {

    private static int NUM_PAGES = 3;

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
                ImageSlideFragment firstSlide = ImageSlideFragment.newInstance("", "Support local youth development", "Hack the Hood introduces low-income youth of color to tech careers by hiring and training them as web consultants who build and promote sites for small local businesses.");
                return firstSlide;

            case 1:
                ImageSlideFragment secondSlide = ImageSlideFragment.newInstance("", "", "Get your business online for free");
                return secondSlide;

            case 2:
                WebsiteTemplatesSlideFragment thirdSlide = WebsiteTemplatesSlideFragment.newInstance();
                return thirdSlide;

            default:
                return null;
        }
    }
}
