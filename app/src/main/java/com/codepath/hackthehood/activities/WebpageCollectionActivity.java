package com.codepath.hackthehood.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.fragments.WebpageCollectionFragment;

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
}
