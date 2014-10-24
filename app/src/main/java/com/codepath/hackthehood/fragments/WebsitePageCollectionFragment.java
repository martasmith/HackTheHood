package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.models.ImageResource;
import com.codepath.hackthehood.models.ParseHelper;
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.WebsitePage;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class WebsitePageCollectionFragment extends NetworkFragment {

    private final static int REQUEST_CODE_TAKE_PHOTO    = 20;
    private final static int REQUEST_CODE_SELECT_PHOTO  = 30;
    public final static String APP_TAG = "HTH_app";

    private EditText etPageText, etDesignerNotes;
    private List<ImageView> imageViews;
    private PopupMenu popup;
    private WebsitePage page;

    private final static String TICK_IMAGE_NAME = "tickImgName";
    private final static String TITLE = "title";
    private final static String PAGE_INDEX = "pageIndex";
    public static WebsitePageCollectionFragment newInstance(String tickImgName, String title, int pageIndex) {
        WebsitePageCollectionFragment fragment = new WebsitePageCollectionFragment();

        Bundle args = new Bundle();
        args.putString  (TICK_IMAGE_NAME, tickImgName);
        args.putString  (TITLE, title);
        args.putInt     (PAGE_INDEX, pageIndex);
        fragment.setArguments(args);

        return fragment;
    }

    public WebsitePageCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetch(true);
    }

    @Override
    public void doFetch(boolean onlyIfNeeded) {
        super.doFetch(onlyIfNeeded);

        final int pageIndex = getArguments().getInt(PAGE_INDEX);
        final User user = (User) ParseUser.getCurrentUser();
        incrementNetworkActivityCount();
        ParseHelper.fetchObjectsInBackgroundInSerial(true,
                new Iterator<ParseObject>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return index != 3;
                    }

                    @Override
                    public ParseObject next() {
                        index++;
                        switch (index) {
                            default:
                                return user;
                            case 2:
                                return user.getWebsite();
                            case 3:
                                return user.getWebsite().getWebsitePages().get(pageIndex);
                        }
                    }

                    @Override
                    public void remove() {
                    }
                }, new GetCallback() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        setFetchIsFinished();
                        decrementNetworkActivityCount();

                        if (e != null) {
                            didReceiveNetworkException(e);
                            return;
                        }

                        page = user.getWebsite().getWebsitePages().get(pageIndex);
                        populateView();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_webpage_collection,container,false);

        etPageText = (EditText) v.findViewById(R.id.etPageText);
        etDesignerNotes = (EditText) v.findViewById(R.id.etDesignerNotes);

        imageViews = new ArrayList<ImageView>();
        imageViews.add((ImageView)v.findViewById(R.id.imgFile1));
        imageViews.add((ImageView)v.findViewById(R.id.imgFile2));
        imageViews.add((ImageView)v.findViewById(R.id.imgFile3));

        setupAddSiteListener((Button)v.findViewById(R.id.btnAddSite));
        for(int c = 0; c < 3; c++)
            setupImgUploadListener(c);

        fetch(true);
        return v;
    }

    private void populateView() {
        List <ImageResource> imageResources = page.getImageResources();
        for(int c = 0; c < 3; c++) {
            Picasso.with(getActivity()).load(imageResources.get(c).getImageUrl()).into(imageViews.get(c));
        }

        etDesignerNotes.setText(page.getNotes());
        etPageText.setText(page.getText());
    }

    private void setupAddSiteListener(final Button bntAddSite) {

        bntAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bntAddSite.setEnabled(false);
                submit(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        bntAddSite.setEnabled(true);

                        if (e != null) {
                            Intent data = new Intent();
                            data.putExtra("tickImgName", getArguments().getString(TICK_IMAGE_NAME));
                            getActivity().setResult(getActivity().RESULT_OK, data);
                            getActivity().finish();
                        }

                    }
                });
            }
        });
    }

    @Override
    public void submit(final SaveCallback saveCallback) {

        page.setNotes(etDesignerNotes.getText().toString());
        page.setTitle(getArguments().getString(TITLE));
        page.setText(etPageText.getText().toString());

        incrementNetworkActivityCount();
        page.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                decrementNetworkActivityCount();
                if (e != null) {
                    didReceiveNetworkException(e);
                }
                saveCallback.done(e);
            }
        });
    }

    private void setupImgUploadListener(final int index) {
        final ImageView imageView = imageViews.get(index);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Create the instance of PopupMenu
                popup = new PopupMenu(getActivity(), imageView);

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
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO + index);
    }

    private void pickFromGallery(int index) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Bring up gallery to select a photo
        startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO + index);
    }

    private void onTakePhotoResult(int index, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            //extract photo that was just taken by the camera
            setBitmap(index, BitmapFactory.decodeFile(getPhotoFileUri(index).getPath()));
        } else { // Result was a failure
            Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onSelectPhotoResult(int index, int resultCode, Intent data) {
        //extract photo that was just picked from the gallery
        try {
            setBitmap(index, MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData()));
        } catch (IOException e) {
            Toast.makeText(getActivity(), "No photo was selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void setBitmap(int index, Bitmap bitmap) {
        imageViews.get(index).setImageBitmap(bitmap);
        incrementNetworkActivityCount();
        page.getImageResources().get(index).setBitmap(bitmap, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                decrementNetworkActivityCount();
                if(e != null) {
                    didReceiveNetworkException(e);
                }
            }
        });
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
}
