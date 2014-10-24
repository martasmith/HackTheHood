package com.codepath.hackthehood.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.fragments.WebsitePageCollectionFragment;

public class WebpageCollectionActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage_collection);

        String title = getIntent().getStringExtra("title");
        int pageIndex = getIntent().getIntExtra("pageIndex", 0);

        getActionBar().setTitle(title);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        WebsitePageCollectionFragment webpageCollectionFragment = WebsitePageCollectionFragment.newInstance(title, pageIndex);
        ft.replace(R.id.flWebpageCollection, webpageCollectionFragment);
        ft.commit();
    }
}
