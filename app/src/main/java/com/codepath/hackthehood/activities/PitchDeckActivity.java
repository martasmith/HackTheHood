package com.codepath.hackthehood.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.adapters.SlidePagerAdapter;
import com.codepath.hackthehood.util.SlidePageTransformer;

/**
 * Created by ravi on 10/06/14.
 */
public class PitchDeckActivity extends FragmentActivity {

    private ViewPager vpSlidePager;
    private SlidePagerAdapter aSlideAdapter;

    private Button btnGetStarted;
    private Button btnBusinessForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_deck);

        // Setup view pager, its adapter, and page transformer
        vpSlidePager = (ViewPager) findViewById(R.id.vpSlidePager);
        aSlideAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        vpSlidePager.setAdapter(aSlideAdapter);
        vpSlidePager.setPageTransformer(true, new SlidePageTransformer());

        // Setup CTAs
        btnGetStarted = (Button) findViewById(R.id.btnGetStarted);
        btnBusinessForm = (Button) findViewById(R.id.btnBusinessForm);
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
        Toast.makeText(getBaseContext(), "You have clicked on Get Started", Toast.LENGTH_SHORT).show();
    }

    public void businessFormBtnClicked(View view) {
        // Open BusinessForm activity
        Intent i = new Intent(PitchDeckActivity.this, BusinessFormActivity.class);
        startActivity(i);
    }
}
