package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.WebpageCollectionActivity;
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.Website;
import com.parse.ParseUser;

import java.io.File;
import java.io.IOException;


public class WebsiteCollectionFragment extends NetworkFragment {

    private AssetFormListener mListener;
    private final int REQUEST_CODE_WEB_CONTENT = 10;
    private final int REQUEST_CODE_TAKE_PHOTO_HEADER = 20;
    private final int REQUEST_CODE_TAKE_PHOTO_LOGO = 21;
    private final int REQUEST_CODE_UPLOAD_PHOTO_HEADER = 30;
    private final int REQUEST_CODE_UPLOAD_PHOTO_LOGO = 31;
    public final String APP_TAG = "HTH_app";

    private String title, tickImgName,businessType,facebookLink,yelpLink,twitterLink,instagramLink;
    private EditText etFacebookLink, etYelpLink, etTwitterLink, etInstagramLink;
    private Spinner sprBusinessType;
    private ImageView ivHeader, ivLogo,checkPage1,checkPage2,checkPage3;
    private Button btnPage1,btnPage2,btnPage3, btnSubmit;
    private PopupMenu popup;
    private Bitmap headerBitmap, logoBitmap;

    public WebsiteCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachFragment(getParentFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_asset_collection, container, false);
        sprBusinessType = (Spinner) v.findViewById(R.id.sprBusinessType);
        etFacebookLink = (EditText) v.findViewById(R.id.etFacebookLink);
        etYelpLink = (EditText) v.findViewById(R.id.etYelpLink);
        etTwitterLink = (EditText) v.findViewById(R.id.etTwitterLink);
        etInstagramLink = (EditText) v.findViewById(R.id.etInstagramLink);
        ivLogo = (ImageView) v.findViewById(R.id.imgLogo);
        ivHeader = (ImageView) v.findViewById(R.id.imgHeader);
        btnPage1 = (Button) v.findViewById(R.id.btnPage1);
        btnPage2 = (Button) v.findViewById(R.id.btnPage2);
        btnPage3 = (Button) v.findViewById(R.id.btnPage3);
        checkPage1 = (ImageView) v.findViewById(R.id.checkPage1);
        checkPage2 = (ImageView) v.findViewById(R.id.checkPage2);
        checkPage3 = (ImageView) v.findViewById(R.id.checkPage3);
        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
        btnSubmit.setEnabled(false);
        setUpSubmitAssetsListener();
        setupPageCreationListener(btnPage1, "checkPage1", 0);
        setupPageCreationListener(btnPage2, "checkPage2", 1);
        setupPageCreationListener(btnPage3, "checkPage3", 2);
        setupImgUploadListener(ivHeader, "photoHeader.jpg", REQUEST_CODE_TAKE_PHOTO_HEADER, REQUEST_CODE_UPLOAD_PHOTO_HEADER);
        setupImgUploadListener(ivLogo,"photoLogo.jpg",REQUEST_CODE_TAKE_PHOTO_LOGO,REQUEST_CODE_UPLOAD_PHOTO_LOGO);
        return v;
    }

    private void setupPageCreationListener(final Button btn, final String checkPage, final int pageIndex) {
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), WebpageCollectionActivity.class);
                title = btn.getText().toString();
                i.putExtra("title", title);
                i.putExtra("tickImgName",checkPage);
                i.putExtra("pageIndex", pageIndex);
                startActivityForResult(i, REQUEST_CODE_WEB_CONTENT);

            }
        });
    }

    private void setUpSubmitAssetsListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAssets();
                if (mListener != null) {
                    mListener.onAssetFormSubmit();
                }
            }
        });
    }

    private void submitAssets() {

        businessType = sprBusinessType.getSelectedItem().toString();
        facebookLink = etFacebookLink.getText().toString();
        yelpLink = etYelpLink.getText().toString();
        twitterLink = etTwitterLink.getText().toString();
        instagramLink = etTwitterLink.getText().toString();

        //get current user
        User user = (User) ParseUser.getCurrentUser();
        Website website = user.getWebsite();
        website.setTypeOfBusiness(businessType);
        website.setFacebookUrl(facebookLink);
        website.setYelpUrl(yelpLink);
        website.setTwitterUrl(twitterLink);
        website.setInstagramUrl(instagramLink);
        website.getLogo().setBitmap(logoBitmap,null);
        website.getHeader().setBitmap(headerBitmap,null);

        // set applicationStatus to APPSTATUS_ASSETS_SUBMITTED
        user.setApplicationStatus(4);
        website.saveEventually();

    }

    private void setupImgUploadListener(final ImageView img, final String photoFileName, final int cameraRequestCode, final int galleryRequestCode) {
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Creating the instance of PopupMenu
                popup = new PopupMenu(getActivity(), img);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.image_popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        //Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                     switch (item.getItemId()) {
                         case R.id.takePhoto:
                         {
                             capturePhoto(photoFileName,cameraRequestCode);
                         }
                         break;
                         case R.id.useExisting:
                         {
                             pickFromGallery(photoFileName, galleryRequestCode);
                         }
                         break;
                     }
                     return true;
                    }
                });

                popup.show();//showing popup menu

            }
        });//closing the setOnClickListener method,

    }

    private void capturePhoto(String photoFileName, int requestCode) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
        // Start the image capture intent to take photo
        startActivityForResult(intent, requestCode);
    }

    private void getCapturedPhoto(String photoFileName, int resultCode, ImageView img) {
        if (resultCode == getActivity().RESULT_OK) {
            //extract photo that was just taken by the camera
            Uri takenPhotoUri = getPhotoFileUri(photoFileName);
            // by this point we have the camera photo on disk
            if (photoFileName.equals("photoHeader.jpg")) {
                headerBitmap = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                // Load the taken image into a preview
                img.setImageBitmap(headerBitmap);
            } else if (photoFileName.equals("photoLogo.jpg")) {
                logoBitmap = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                // Load the taken image into a preview
                img.setImageBitmap(logoBitmap);
            }
        } else {
            // Result was a failure
            Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickFromGallery(String photoFileName, int requestCode) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Bring up gallery to select a photo
        startActivityForResult(intent, requestCode);
    }


    private void getPickedFromGallery(Intent data, ImageView img) {
        //extract photo that was just picked from the gallery
        if (data != null) {
            Uri photoUri = data.getData();
            // Do something with the photo based on Uri
            try {
                if (img.getDrawable() == ivHeader.getDrawable()) {
                    headerBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                    // Load the selected image into a preview
                    img.setImageBitmap(headerBitmap);
                } else if (img.getDrawable() == ivLogo.getDrawable()) {
                    logoBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                    // Load the selected image into a preview
                    img.setImageBitmap(logoBitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "No photo was selected", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_WEB_CONTENT && resultCode == getActivity().RESULT_OK) {
            tickImgName = data.getExtras().getString("tickImgName");
            //Toast.makeText(getActivity(), "tickImgName= " + tickImgName, Toast.LENGTH_LONG).show();
            if (tickImgName.equals("checkPage1")) {
                  checkPage1.setVisibility(View.VISIBLE);
                  checkAllWebpagesVisited();
            } else if (tickImgName.equals("checkPage2")) {
                checkPage2.setVisibility(View.VISIBLE);
                checkAllWebpagesVisited();
            } else if (tickImgName.equals("checkPage3")) {
                checkPage3.setVisibility(View.VISIBLE);
                checkAllWebpagesVisited();
            }
        }
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO_HEADER) {
                getCapturedPhoto("photoHeader.jpg",resultCode,ivHeader);
        }
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO_LOGO) {
                getCapturedPhoto("photoLogo.jpg",resultCode,ivLogo);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_HEADER && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data, ivHeader);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_LOGO && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,ivLogo);
        }

    }

    public void checkAllWebpagesVisited() {
        // while none of the input fields are required, check to make sure the user visited all website page views
        // before we allow them to submit
        if ((checkPage1.getVisibility() == View.VISIBLE) &&
                (checkPage2.getVisibility() == View.VISIBLE) && (checkPage3.getVisibility() == View.VISIBLE)) {
            btnSubmit.setEnabled(true);
        }
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }

    public void onAttachFragment(Fragment fragment) {
        try {
            mListener = (AssetFormListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement AssetFormListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface AssetFormListener {
        public void onAssetFormSubmit();
    }
}
