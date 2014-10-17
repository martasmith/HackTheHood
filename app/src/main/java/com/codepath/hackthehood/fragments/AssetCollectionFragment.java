package com.codepath.hackthehood.fragments;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.util.Log;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;
import android.provider.MediaStore;
import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.AssetCollectionActivity;
import com.codepath.hackthehood.activities.WebpageCollectionActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AssetCollectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AssetCollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AssetCollectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int REQUEST_CODE_WEB_CONTENT = 10;
    private final int REQUEST_CODE_TAKE_PHOTO_HEADER = 20;
    private final int REQUEST_CODE_TAKE_PHOTO_LOGO = 21;
    private final int REQUEST_CODE_TAKE_PHOTO_MORE = 22;
    private final int REQUEST_CODE_UPLOAD_PHOTO_HEADER = 30;
    private final int REQUEST_CODE_UPLOAD_PHOTO_LOGO = 31;
    private final int REQUEST_CODE_UPLOAD_PHOTO_MORE = 32;
    public final String APP_TAG = "HTH_app";


    private String mParam1;
    private String mParam2;
    private String title, tickImgName;
    private EditText etFacebookLink, etYelpLink, etTwitterLink, etInstagramLink;
    private Spinner sprBusinessType;
    private ImageView ivHeader, ivLogo, ivMore, checkPage1,checkPage2,checkPage3;
    private Button btnPage1,btnPage2,btnPage3, btnSubmit;
    private PopupMenu popup;
    public String photoFileName = "photo.jpg";


    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssetCollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssetCollectionFragment newInstance(String param1, String param2) {
        AssetCollectionFragment fragment = new AssetCollectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public AssetCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_asset_collection, container, false);
        View v = inflater.inflate(R.layout.fragment_asset_collection, container, false);
        sprBusinessType = (Spinner) v.findViewById(R.id.sprBusinessType);
        etFacebookLink = (EditText) v.findViewById(R.id.etFacebookLink);
        etYelpLink = (EditText) v.findViewById(R.id.etYelpLink);
        etTwitterLink = (EditText) v.findViewById(R.id.etTwitterLink);
        etInstagramLink = (EditText) v.findViewById(R.id.etInstagramLink);
        ivLogo = (ImageView) v.findViewById(R.id.imgLogo);
        ivHeader = (ImageView) v.findViewById(R.id.imgHeader);
        ivMore = (ImageView) v.findViewById(R.id.imgMore);
        btnPage1 = (Button) v.findViewById(R.id.btnPage1);
        btnPage2 = (Button) v.findViewById(R.id.btnPage2);
        btnPage3 = (Button) v.findViewById(R.id.btnPage3);
        checkPage1 = (ImageView) v.findViewById(R.id.checkPage1);
        checkPage2 = (ImageView) v.findViewById(R.id.checkPage2);
        checkPage3 = (ImageView) v.findViewById(R.id.checkPage3);
        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
        setUpSubmitAssetsListener();
        setupPageCreationListener(btnPage1,"checkPage1");
        setupPageCreationListener(btnPage2,"checkPage2");
        setupPageCreationListener(btnPage3,"checkPage3");

        setupImgUploadListener(ivHeader,REQUEST_CODE_TAKE_PHOTO_HEADER,REQUEST_CODE_UPLOAD_PHOTO_HEADER);
        setupImgUploadListener(ivLogo,REQUEST_CODE_TAKE_PHOTO_LOGO,REQUEST_CODE_UPLOAD_PHOTO_LOGO);
        setupImgUploadListener(ivMore,REQUEST_CODE_TAKE_PHOTO_MORE,REQUEST_CODE_UPLOAD_PHOTO_MORE);
        return v;
    }

    private void setupPageCreationListener(final Button btn, final String checkPage) {
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), WebpageCollectionActivity.class);
                title = btn.getText().toString();
                i.putExtra("title",title);
                i.putExtra("tickImgName",checkPage);
                startActivityForResult(i, REQUEST_CODE_WEB_CONTENT);

            }
        });
    }

    private void setUpSubmitAssetsListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You have clicked on Submit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupImgUploadListener(final ImageView img, final int cameraRequestCode, final int galleryRequestCode) {
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
                             capturePhoto(cameraRequestCode);
                         }
                         break;
                         case R.id.useExisting:
                         {
                             pickFromGallery(galleryRequestCode);
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

    private void capturePhoto(int requestCode) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
        // Start the image capture intent to take photo
        startActivityForResult(intent, requestCode);
    }

    private void getCapturedPhoto(int resultCode, ImageView img) {
        if (resultCode == getActivity().RESULT_OK) {
            //extract photo that was just taken by the camera
            Uri takenPhotoUri = getPhotoFileUri(photoFileName);
            // by this point we have the camera photo on disk
            Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
            // Load the taken image into a preview
            img.setImageBitmap(takenImage);
        } else { // Result was a failure
            Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickFromGallery(int requestCode) {
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
            Bitmap selectedImage = null;
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                // Load the selected image into a preview
                img.setImageBitmap(selectedImage);
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
            } else if (tickImgName.equals("checkPage2")) {
                checkPage2.setVisibility(View.VISIBLE);
            } else if (tickImgName.equals("checkPage3")) {
                checkPage3.setVisibility(View.VISIBLE);
            }
        }
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO_HEADER) {
                getCapturedPhoto(resultCode,ivHeader);
        }
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO_LOGO) {
                getCapturedPhoto(resultCode,ivLogo);
        }
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO_MORE) {
                getCapturedPhoto(resultCode,ivMore);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_HEADER && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,ivHeader);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_LOGO && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,ivLogo);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_MORE && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,ivMore);
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

    /*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    */
}
