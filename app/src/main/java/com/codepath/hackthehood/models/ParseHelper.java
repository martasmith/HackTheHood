package com.codepath.hackthehood.models;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Iterator;

/**
 * Created by thomasharte on 20/10/2014.
 */
public class ParseHelper {

    static public void fetchObjectsInBackgroundInParallel(final ParseObject[] objects, final GetCallback getCallback) {
        GetCallback parallelCallback = new GetCallback() {

            private int numberOfObjectsRemaining = objects.length;
            private boolean hasPosted = false;

            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(hasPosted) return;

                if(e != null) {
                    hasPosted = true;
                    getCallback.done(parseObject, e);
                }

                numberOfObjectsRemaining--;
                if(numberOfObjectsRemaining == 0) {
                    getCallback.done(parseObject, null);
                }
            }
        };

        for(ParseObject object : objects)
            object.fetchInBackground(parallelCallback);
    }

    static public void saveObjectsInBackgroundInParallel(final ParseObject[] objects, final SaveCallback saveCallback) {
        SaveCallback parallelCallback = new SaveCallback() {

            private int numberOfObjectsRemaining = objects.length;
            private boolean hasPosted = false;

            @Override
            public void done(ParseException e) {
                if(hasPosted) return;

                if(e != null) {
                    hasPosted = true;
                    saveCallback.done(e);
                }

                numberOfObjectsRemaining--;
                if(numberOfObjectsRemaining == 0) {
                    saveCallback.done(null);
                }
            }
        };

        for(ParseObject object : objects)
            object.saveInBackground(parallelCallback);
    }

    static public void fetchObjectsInBackgroundInSerial(final Iterator<ParseObject> objects, final GetCallback getCallback) {
        class SerialGetCallback extends GetCallback {

            private int listIndex = 0;

            public void startNextFetch() {
                if(!objects.hasNext()) {
                    getCallback.done(null, null);
                    return;
                }

                ParseObject nextObject = objects.next();
                nextObject.fetchInBackground(this);
            }

            @Override
            public void done(ParseObject parseObject, ParseException e) {

                if(e != null) {
                    getCallback.done(parseObject, e);
                } else {
                    startNextFetch();
                }
            }
        };
        new SerialGetCallback().startNextFetch();
    }

}
