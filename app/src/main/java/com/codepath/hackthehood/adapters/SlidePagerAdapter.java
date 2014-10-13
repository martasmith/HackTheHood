package com.codepath.hackthehood.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.hackthehood.fragments.ImageSlideFragment;

/**
 * Created by ravi on 10/12/14.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {

    private static int NUM_PAGES = 2;

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
                ImageSlideFragment firstSlide = ImageSlideFragment.newInstance("", "Get your business online for free");
                return firstSlide;

            case 1:
                ImageSlideFragment secondSlide = ImageSlideFragment.newInstance("", "Support local youth development");
                return secondSlide;

            default:
                return null;
        }
    }
}
