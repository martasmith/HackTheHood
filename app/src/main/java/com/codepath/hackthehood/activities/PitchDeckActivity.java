package com.codepath.hackthehood.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.hackthehood.R;


public class PitchDeckActivity extends Activity {

    private Button btnGetStarted,btnBusinessForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pitch_deck);
        btnGetStarted = (Button) findViewById(R.id.btnGetStarted);
        btnBusinessForm = (Button) findViewById(R.id.btnBusinessForm);
        setUpGetStartedListener();
        setUpBusinessFormListener();
    }

    private void setUpGetStartedListener() {
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"You have clicked on Get Started", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpBusinessFormListener() {
        btnBusinessForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PitchDeckActivity.this, BusinessFormActivity.class);
                startActivity(i);
            }
        });
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
    }

    public void businessFormBtnClicked(View view) {
        // Open BusinessForm activity
    }
}
