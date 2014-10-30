package com.codepath.hackthehood.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.codepath.hackthehood.R;
import com.codepath.hackthehood.fragments.forms.WebsitePageFragment;
import com.codepath.hackthehood.fragments.info.WebviewFragment;

public class FinalWebsiteViewerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_website_viewer);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle websiteFragmentArguments = new Bundle();
        websiteFragmentArguments.putString(WebviewFragment.PAGE_TITLE, "Your website");
        websiteFragmentArguments.putString(WebviewFragment.PAGE_URL, "http://bergeronbooks.weebly.com");

        WebviewFragment webviewFragment = new WebviewFragment();
        webviewFragment.setArguments(websiteFragmentArguments);
        fragmentTransaction.replace(R.id.flWebviewFragmentContainer, webviewFragment);

        fragmentTransaction.commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.final_website_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            overridePendingTransition (R.anim.open_left, R.anim.close_right);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition (R.anim.open_left, R.anim.close_right);
    }
}
