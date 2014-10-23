package com.codepath.hackthehood.models;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

@ParseClassName("ImageResource")
public class ImageResource extends NullForNothingParseObject {

    public ImageResource() {}

    /*
        Exposed methods:

            String getBitmapUrl
            void setBitmap(Bitmap, SaveCallback)
     */

    private final static String bitmapKey = "bitmap";
    private int setCount = 0;
    public void setBitmap(final Bitmap bitmap, final SaveCallback saveCallback) {
        if(bitmap == null) {
            this.remove(bitmapKey);
            return;
        }

        final ImageResource thisResource = this;

        setCount++;
        final int setCountIdentifier = setCount;
        AsyncTask<Void, Void, ByteArrayOutputStream> newtask = new AsyncTask<Void, Void, ByteArrayOutputStream>() {
            @Override
            protected ByteArrayOutputStream doInBackground(Void... voids) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                return stream;
            }

            @Override
            protected void onPostExecute(ByteArrayOutputStream byteArrayOutputStream) {
                if(setCountIdentifier != thisResource.setCount) return;

                final ParseFile imageFile = new ParseFile("image.jpg", byteArrayOutputStream.toByteArray());
                imageFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(setCountIdentifier != thisResource.setCount) return;

                        if(e != null) {
                            if(saveCallback != null)
                                saveCallback.done(e);
                        } else {
                            thisResource.put(bitmapKey, imageFile);
                            if(saveCallback != null)
                                saveCallback.done(null);
                        }
                    }
                });
            }
        };
        newtask.execute();
    }
    public String getBitmapUrl() {
        ParseFile file = (ParseFile)get(bitmapKey);
        return (file != null) ? file.getUrl() : null;
    }
}
