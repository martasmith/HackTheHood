package com.codepath.hackthehood.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by thomasharte on 23/10/2014.
 */
public class BitmapRotator {
    public static Bitmap getNormalOrientationBitmap(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        return getRotatedBitmap(photoFilePath,bm,bounds);
    }

    public static Bitmap getRotatedBitmap(String photoFilePath, Bitmap bm, BitmapFactory.Options bounds) {

        // Read EXIF Data
        ExifInterface exif;
        String orientString = "";

        try {
            exif = new ExifInterface(photoFilePath);
            orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);

        } catch (IOException e) {
            e.printStackTrace();
        }

        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        // Return result
        if (bounds == null) {
            return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } else {
            return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        }

    }
}
