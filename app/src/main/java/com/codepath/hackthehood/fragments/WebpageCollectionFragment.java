package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.models.ImageResource;
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.WebsitePage;
import com.codepath.hackthehood.util.BitmapScaler;
import com.parse.ParseUser;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class WebpageCollectionFragment extends Fragment {

    private final int REQUEST_CODE_TAKE_PHOTO_1 = 20;
    private final int REQUEST_CODE_TAKE_PHOTO_2 = 21;
    private final int REQUEST_CODE_TAKE_PHOTO_3 = 22;
    private final int REQUEST_CODE_UPLOAD_PHOTO_1 = 30;
    private final int REQUEST_CODE_UPLOAD_PHOTO_2 = 31;
    private final int REQUEST_CODE_UPLOAD_PHOTO_3 = 32;
    public final String APP_TAG = "HTH_app";

    // TODO: Rename and change types of parameters
    private String tickImgName, title, pageText,designerNotes;
    private EditText etPageText, etDesignerNotes;
    private ImageView ivFileUpload1,ivFileUpload2,ivFileUpload3;
    private Button btnAddSite;
    private PopupMenu popup;
    private Bitmap photo1Bitmap, photo2Bitmap, photo3Bitmap;
    private WebsitePage page;


    public static WebpageCollectionFragment newInstance(String tickImgName, String title, int pageIndex) {
        WebpageCollectionFragment fragment = new WebpageCollectionFragment();
        Bundle args = new Bundle();
        args.putString("tickImgName", tickImgName);
        args.putString("title", title);
        args.putInt("pageIndex", pageIndex);
        fragment.setArguments(args);
        return fragment;
    }
    public WebpageCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tickImgName = getArguments().getString("tickImgName");
            title = getArguments().getString("title");

            int pageIndex = getArguments().getInt("pageIndex");
            User user = (User) ParseUser.getCurrentUser();
            try {
                user.fetch();
                user.getWebsite().fetch();
            } catch(Exception e) {}
            page = user.getWebsite().getWebsitePages().get(pageIndex);
            try {
                page.fetch();
            } catch(Exception e) {}

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_webpage_collection,container,false);
        etPageText = (EditText) v.findViewById(R.id.etPageText);
        etDesignerNotes = (EditText) v.findViewById(R.id.etDesignerNotes);
        ivFileUpload1 = (ImageView) v.findViewById(R.id.imgFile1);
        ivFileUpload2 = (ImageView) v.findViewById(R.id.imgFile2);
        ivFileUpload3 = (ImageView) v.findViewById(R.id.imgFile3);
        btnAddSite = (Button) v.findViewById(R.id.btnAddSite);
        setupAddSiteListener();
        setupImgUploadListener(ivFileUpload1, "photoFile1.jpg", REQUEST_CODE_TAKE_PHOTO_1, REQUEST_CODE_UPLOAD_PHOTO_1);
        setupImgUploadListener(ivFileUpload2,"photoFile2.jpg",REQUEST_CODE_TAKE_PHOTO_2,REQUEST_CODE_UPLOAD_PHOTO_2);
        setupImgUploadListener(ivFileUpload3,"photoFile3.jpg",REQUEST_CODE_TAKE_PHOTO_3,REQUEST_CODE_UPLOAD_PHOTO_3);
        return v;
    }

    private void setupAddSiteListener() {
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitWebpageData();
                Intent data = new Intent();
                data.putExtra("tickImgName", tickImgName);
                getActivity().setResult(getActivity().RESULT_OK, data);
                getActivity().finish();

            }
        });
    }

    private void submitWebpageData() {

        pageText = etPageText.getText().toString();
        designerNotes = etDesignerNotes.getText().toString();

        if (page != null) {
            page.setNotes(designerNotes);
            page.setTitle(title);
            page.setText(pageText);
            page.setNotes(designerNotes);
            List<ImageResource> imageResources = page.getImageResources();
            int num = imageResources.size();
            imageResources.get(0).setBitmap(photo1Bitmap,null);
            imageResources.get(1).setBitmap(photo2Bitmap,null);
            imageResources.get(2).setBitmap(photo2Bitmap,null);

            try {
                page.save();
            } catch(Exception e) {}
        }
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
                                pickFromGallery(photoFileName,galleryRequestCode);
                            }
                            break;
                        }

                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });//closing the setOnClickListener method

    }

    private void capturePhoto(String photoFileName, int requestCode) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
        // Start the image capture intent to take photo
        startActivityForResult(intent, requestCode);
    }

    private void getCapturedPhoto(String photoFileName, Bitmap bMap, int resultCode, ImageView img) {
        if (resultCode == getActivity().RESULT_OK) {
            //extract photo that was just taken by the camera
            Uri takenPhotoUri = getPhotoFileUri(photoFileName);

            // by this point we have the camera photo on disk, rotate image to correct position
            bMap = rotateBitmapOrientation(takenPhotoUri.getPath());

            // Resize the bitmap
            //Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 85, 85, true);
            Bitmap bMapScaled =  BitmapScaler.scaleToFill(bMap, 85, 85);


            // Loads the re-sized Bitmap into an ImageView
            img.setImageBitmap(bMapScaled);
        } else { // Result was a failure
            Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickFromGallery(String photoFileName, int requestCode) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Bring up gallery to select a photo
        startActivityForResult(intent, requestCode);
    }

    private void getPickedFromGallery(Intent data, Bitmap bMap, ImageView img) {
        //extract photo that was just picked from the gallery
        if (data != null) {
            Uri photoUri = data.getData();
            try {
                bMap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);

                // Resize the bitmap
                //Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 85, 85, true);
                Bitmap bMapScaled =  BitmapScaler.scaleToFill(bMap, 85, 85);

                // Loads the re-sized Bitmap into an ImageView
                img.setImageBitmap(bMapScaled);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getActivity(), "No photo was selected", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
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
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_TAKE_PHOTO_1) {
            getCapturedPhoto("photoFile1.jpg",photo1Bitmap,resultCode,ivFileUpload1);
        }
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO_2) {
            getCapturedPhoto("photoFile2.jpg",photo2Bitmap,resultCode,ivFileUpload2);
        }
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO_3) {
            getCapturedPhoto("photoFile3.jpg",photo3Bitmap, resultCode,ivFileUpload3);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_1 && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,photo1Bitmap,ivFileUpload1);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_2 && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,photo2Bitmap,ivFileUpload2);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_3 && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,photo3Bitmap,ivFileUpload3);
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
}
