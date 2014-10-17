package com.codepath.hackthehood;

import android.content.Context;
import android.util.Log;

import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.Website;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by ravi on 10/13/14.
 */
public class HTHApplication extends com.activeandroid.app.Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        HTHApplication.context = this;
        Parse.initialize(this, "cYC431dctIjsXhzHUtXnkBciTCoywHuXFN1aOLyl", "LO7mpm3umhZbouNxl7Uz4eP0ERI3cEt70dMEzNOR");

        // register custom Parse subclasses
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Website.class);

        // subscribe for push
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.d("com.parse.push", "Successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "Failed to subscribe for push", e);
                }
            }
        });
    }
}
