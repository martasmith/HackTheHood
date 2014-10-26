package com.codepath.hackthehood.fragments.forms;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.fragments.NetworkFragment;
import com.codepath.hackthehood.models.ImageResource;
import com.codepath.hackthehood.util.BitmapRotator;
import com.codepath.hackthehood.util.BitmapScaler;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.io.File;

/**
 * Created by thomasharte on 23/10/2014.
 */
public abstract class ImageResourceFragment extends NetworkFragment {

    private final static int REQUEST_CODE_TAKE_PHOTO    = 20;
    private final static int REQUEST_CODE_SELECT_PHOTO  = 30;
    private final static String APP_TAG = "HTH_app";

    protected void setupImageUploadListener(final ImageView imageView, final int index) {

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Create the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), imageView);

                //Inflate the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.image_popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.takePhoto:
                                takePhoto(index);
                                break;
                            case R.id.useExisting:
                                pickFromGallery(index);
                                break;
                        }

                        return true;
                    }
                });

                popup.show(); //show the popup
            }
        });//closing the setOnClickListener method
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode >= REQUEST_CODE_TAKE_PHOTO && requestCode <= REQUEST_CODE_TAKE_PHOTO + 3) {
            onTakePhotoResult(requestCode - REQUEST_CODE_TAKE_PHOTO, resultCode, data);
        }

        if(requestCode >= REQUEST_CODE_SELECT_PHOTO && requestCode <= REQUEST_CODE_SELECT_PHOTO + 3) {
            onSelectPhotoResult(requestCode - REQUEST_CODE_SELECT_PHOTO, resultCode, data);
        }
    }
    private void takePhoto(int index) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(index)); // set the image file name
        getParentFragment().startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO + index);
    }

    private void pickFromGallery(int index) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Bring up gallery to select a photo
        getParentFragment().startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO + index);
    }

    private void onTakePhotoResult(int index, int resultCode, Intent data) {
        //extract photo that was just taken by the camera
        if (resultCode == Activity.RESULT_OK) {
            try {
                setBitmap(index, BitmapRotator.getNormalOrientationBitmap(getPhotoFileUri(index).getPath()));
            } catch (Exception e) {}
        }
    }

    private void onSelectPhotoResult(int index, int resultCode, Intent data) {
        //extract photo that was just picked from the gallery
        if(resultCode == Activity. RESULT_OK) {
            try {
                Uri photoUri = data.getData();
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                setBitmap(index, BitmapRotator.getRotatedBitmap(photoUri.toString(),bm,null));
            } catch (Exception e) {}
        }
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(int index) {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + "image" + index + ".jpg"));
    }

    protected abstract ImageView imageViewForIndex(int index);
    protected abstract ImageResource imageResourceForIndex(int index);

    protected void setBitmap(int index, Bitmap bitmap) {

        ImageView imageView = imageViewForIndex(index);
        imageView.setImageBitmap(BitmapScaler.scaleToFill(bitmap, imageView.getWidth(), imageView.getHeight()));

        final ImageResource imageResource = imageResourceForIndex(index);

        incrementNetworkActivityCount();
        imageResource.setBitmap(bitmap, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                didReceiveNetworkException(e);
                if (e != null) {
                    decrementNetworkActivityCount();
                    return;
                }

                imageResource.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        didReceiveNetworkException(e);
                        decrementNetworkActivityCount();
                    }
                });
            }
        });
    }
}
