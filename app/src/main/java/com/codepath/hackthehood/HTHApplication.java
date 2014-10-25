package com.codepath.hackthehood;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.codepath.hackthehood.models.Address;
import com.codepath.hackthehood.models.ImageResource;
import com.codepath.hackthehood.models.PageResource;
import com.codepath.hackthehood.models.StringResource;
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.Website;
import com.codepath.hackthehood.models.WebsitePage;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by ravi on 10/13/14.
 */
public class HTHApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "cYC431dctIjsXhzHUtXnkBciTCoywHuXFN1aOLyl", "LO7mpm3umhZbouNxl7Uz4eP0ERI3cEt70dMEzNOR");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        // register custom Parse subclasses
        ParseObject.registerSubclass(Address.class);
        ParseObject.registerSubclass(ImageResource.class);
        ParseObject.registerSubclass(PageResource.class);
        ParseObject.registerSubclass(StringResource.class);
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Website.class);
        ParseObject.registerSubclass(WebsitePage.class);

        // subscribe for push
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "Successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "Failed to subscribe for push", e);
                }
            }
        });
    }
}
