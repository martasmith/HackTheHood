package com.codepath.hackthehood.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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

        // Setup view pager, its adapter, and page transformer
        vpSlidePager = (ViewPager) findViewById(R.id.vpSlidePager);
        aSlideAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        vpSlidePager.setAdapter(aSlideAdapter);
        vpSlidePager.setPageTransformer(true, new SlidePageTransformer());

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
