package com.codepath.hackthehood.fragments;

import android.app.Activity;
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

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebpageCollectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebpageCollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class WebpageCollectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int REQUEST_CODE_WEB_CONTENT = 10;
    private final int REQUEST_CODE_TAKE_PHOTO_1 = 20;
    private final int REQUEST_CODE_TAKE_PHOTO_2 = 21;
    private final int REQUEST_CODE_TAKE_PHOTO_3 = 22;
    private final int REQUEST_CODE_UPLOAD_PHOTO_1 = 30;
    private final int REQUEST_CODE_UPLOAD_PHOTO_2 = 31;
    private final int REQUEST_CODE_UPLOAD_PHOTO_3 = 32;
    public final String APP_TAG = "HTH_app";

    // TODO: Rename and change types of parameters
    private String tickImgName;
    private EditText etPageText, etDesignerNotes;
    private ImageView ivFileUpload1,ivFileUpload2,ivFileUpload3;
    private Button btnAddSite;
    private PopupMenu popup;
    public String photoFileName = "photo2.jpg";

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment WebpageCollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebpageCollectionFragment newInstance(String tickImgName) {
        WebpageCollectionFragment fragment = new WebpageCollectionFragment();
        Bundle args = new Bundle();
        args.putString("tickImgName", tickImgName);
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
        setupImgUploadListener(ivFileUpload1,REQUEST_CODE_TAKE_PHOTO_1,REQUEST_CODE_UPLOAD_PHOTO_1);
        setupImgUploadListener(ivFileUpload2,REQUEST_CODE_TAKE_PHOTO_2,REQUEST_CODE_UPLOAD_PHOTO_2);
        setupImgUploadListener(ivFileUpload3,REQUEST_CODE_TAKE_PHOTO_3,REQUEST_CODE_UPLOAD_PHOTO_3);
        return v;
    }

    private void setupAddSiteListener() {
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("tickImgName", tickImgName);
                getActivity().setResult(getActivity().RESULT_OK, data);
                getActivity().finish();

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
        if (requestCode == REQUEST_CODE_TAKE_PHOTO_1) {
            getCapturedPhoto(resultCode,ivFileUpload1);
        }
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO_2) {
            getCapturedPhoto(resultCode,ivFileUpload2);
        }
        else if (requestCode == REQUEST_CODE_TAKE_PHOTO_3) {
            getCapturedPhoto(resultCode,ivFileUpload3);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_1 && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,ivFileUpload1);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_2 && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,ivFileUpload2);
        }
        else if (requestCode == REQUEST_CODE_UPLOAD_PHOTO_3 && resultCode == getActivity().RESULT_OK) {
            getPickedFromGallery(data,ivFileUpload3);
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
