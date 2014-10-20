package com.codepath.hackthehood.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.fragments.WebpageCollectionFragment;
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.WebsitePage;
import com.parse.ParseUser;

public class WebpageCollectionActivity extends FragmentActivity {

    private WebpageCollectionFragment webpageCollectionFragment;
    private String title,tickImgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage_collection);

        title = getIntent().getStringExtra("title");
        tickImgName = getIntent().getStringExtra("tickImgName");
        int pageIndex = getIntent().getIntExtra("pageIndex", 0);

        getActionBar().setTitle(title);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        webpageCollectionFragment = WebpageCollectionFragment.newInstance(tickImgName, title, pageIndex);
        ft.replace(R.id.flWebpageCollection, webpageCollectionFragment);
        ft.commit();
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webpage_collection, menu);
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
    */
}
