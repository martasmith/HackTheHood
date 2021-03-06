package com.codepath.hackthehood.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.hackthehood.fragments.slides.GetBusinessOnlineSlideFragment;
import com.codepath.hackthehood.fragments.slides.ImageSlideFragment;
import com.codepath.hackthehood.fragments.slides.MapsSlideFragment;
import com.codepath.hackthehood.fragments.slides.VideoSlideFragment;
import com.codepath.hackthehood.fragments.slides.WebsiteTemplatesSlideFragment;

/**
 * Created by ravi on 10/12/14.
 */
public class SlidePagerAdapter extends FragmentPagerAdapter {

    private static final int NUM_PAGES = 5;

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
                return ImageSlideFragment.newInstance("Hack the Hood introduces low-income youth of color to tech careers by hiring and training them as web consultants who build and promote sites for small local businesses.");

            case 1:
                return WebsiteTemplatesSlideFragment.newInstance();

            case 2:
                return new GetBusinessOnlineSlideFragment();

            case 3:
                return new VideoSlideFragment();

            case 4:
                return new MapsSlideFragment();

            default:
                return null;
        }
    }
}
