package com.codepath.hackthehood.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.adapters.SlidePagerAdapter;
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.util.SlidePageTransformer;
import com.parse.ParseUser;

/**
 * Created by ravi on 10/06/14.
 */
public class PitchDeckActivity extends FragmentActivity {

    private ViewPager vpSlidePager;
    private SlidePagerAdapter aSlideAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_deck);

        final View CTAContainer = (View) findViewById(R.id.llCTAContainer);
        // Setup view pager, its adapter, and page transformer
        vpSlidePager = (ViewPager) findViewById(R.id.vpSlidePager);
        aSlideAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        vpSlidePager.setAdapter(aSlideAdapter);
        vpSlidePager.setPageTransformer(true, new SlidePageTransformer());
//        vpSlidePager.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (vpSlidePager.getCurrentItem() < vpSlidePager.getAdapter().getCount() - 1) {
//                    vpSlidePager.setCurrentItem(vpSlidePager.getCurrentItem() + 1, true);
//                }
//            }
//        });
        vpSlidePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("INFO", "Position = " + position + " Offset = " + positionOffset + " Pixels = " + positionOffsetPixels);
                switch (position) {
                    case 0:
                        // Keep it off screen
                        CTAContainer.setTranslationY(CTAContainer.getMeasuredHeight());
                        CTAContainer.setAlpha(0);
                        break;
                    case 1:
                        // Start moving to the top
                        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
                        CTAContainer.setTranslationY(CTAContainer.getMeasuredHeight() * decelerateInterpolator.getInterpolation(1 - positionOffset));
                        CTAContainer.setAlpha(positionOffset);
                        break;
                    case 2:
                        // Display on final position
                        CTAContainer.setTranslationY(0);
                        CTAContainer.setAlpha(1);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // skip straight to the business form if there's a current user,
        // pretending that this activity never happened
        User user = (User) ParseUser.getCurrentUser();
        if(user != null) {
            startActivity(new Intent(this, BusinessFormActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pitch_deck, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getStartedClicked(View view) {
        // Open Login/Signup activity
        Intent i = new Intent(PitchDeckActivity.this, LoginSignupActivity.class);
        startActivity(i);
    }
}
