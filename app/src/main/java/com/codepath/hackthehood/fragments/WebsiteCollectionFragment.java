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
import com.codepath.hackthehood.controller.ParseHelper;
import com.codepath.hackthehood.models.ImageResource;
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.Website;
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


public class WebsiteCollectionFragment extends ImageCollectionFragment {

    private AssetFormListener mListener;
    private final int REQUEST_CODE_WEB_CONTENT = 10;

    private EditText etFacebookLink, etYelpLink, etTwitterLink, etInstagramLink;
    private Spinner sprBusinessType;
    private ImageView ivHeader, ivLogo;
    private List<ImageView> checkPages;
    private List<Button> btnPages;
    private Button btnSubmit;

    public WebsiteCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachFragment(getParentFragment());
    }

    @Override
    public void doFetch(boolean onlyIfNeeded) {
        super.doFetch(onlyIfNeeded);

        final User user = (User) ParseUser.getCurrentUser();
        incrementNetworkActivityCount();
        ParseHelper.fetchObjectsInBackgroundInSerial(true,
                new Iterator<ParseObject>() {
                    private int index = 0;

                    @Override
                    public boolean hasNext() {
                        return index != 2;
                    }

                    @Override
                    public ParseObject next() {
                        switch(index) {
                            default: return user;
                            case 1: return user.getWebsite();
                        }
                    }

                    @Override
                    public void remove() {

                    }
                },
                new GetCallback() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        if (e != null) {
                            setFetchIsFinished();
                            decrementNetworkActivityCount();
                            didReceiveNetworkException(e);
                            return;
                        }

                        Website website = user.getWebsite();
                        ImageResource imageResources[] = {website.getLogo(), website.getHeader()};
                        ParseHelper.fetchObjectsInBackgroundInParallel(
                                true,
                                imageResources,
                                new GetCallback() {
                                    @Override
                                    public void done(ParseObject parseObject, ParseException e) {
                                        setFetchIsFinished();
                                        decrementNetworkActivityCount();

                                        if (e != null) {
                                            didReceiveNetworkException(e);
                                        }

                                        populateView();
                                    }
                                });
                    }
                });
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

        btnPages = new ArrayList<Button>();
        btnPages.add((Button)v.findViewById(R.id.btnPage1));
        btnPages.add((Button)v.findViewById(R.id.btnPage2));
        btnPages.add((Button)v.findViewById(R.id.btnPage3));

        checkPages = new ArrayList<ImageView>();
        checkPages.add((ImageView) v.findViewById(R.id.checkPage1));
        checkPages.add((ImageView) v.findViewById(R.id.checkPage2));
        checkPages.add((ImageView) v.findViewById(R.id.checkPage3));

        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
        btnSubmit.setEnabled(false);
        setUpSubmitAssetsListener();

        for(int c = 0; c < btnPages.size(); c++)
            setupPageCreationListener(btnPages.get(c), c);

        setupImageUploadListener(ivHeader, 0);
        setupImageUploadListener(ivLogo, 1);

        doFetch(true);
        return v;
    }

    @Override
    protected ImageView imageViewForIndex(int index) {
        return (index == 1) ? ivLogo : ivHeader;
    }

    @Override
    protected ImageResource imageResourceForIndex(int index) {
        User user = (User) ParseUser.getCurrentUser();
        Website website = user.getWebsite();
        return (index == 1) ? website.getLogo() : website.getHeader();
    }

    private void setupPageCreationListener(final Button btn, final int pageIndex) {
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), WebpageCollectionActivity.class);
                i.putExtra("title", btn.getText().toString());
                i.putExtra("pageIndex", pageIndex);
                startActivityForResult(i, REQUEST_CODE_WEB_CONTENT + pageIndex);
            }
        });
    }

    private void setUpSubmitAssetsListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User) ParseUser.getCurrentUser();
                user.setApplicationStatus(User.APPSTATUS_ASSETS_SUBMITTED);

                btnSubmit.setEnabled(false);
                submit(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (mListener != null && e == null) {
                            mListener.onAssetFormSubmit();
                        }
                    }
                });

            }
        });
    }

    private void populateView() {
        User user = (User) ParseUser.getCurrentUser();
        Website website = user.getWebsite();

        // TODO : type of business
//        sprBusinessType.setSel
//        website.setTypeOfBusiness(sprBusinessType.getSelectedItem().toString());

        etFacebookLink.setText(website.getFacebookUrl());
        etYelpLink.setText(website.getYelpUrl());
        etTwitterLink.setText(website.getTwitterUrl());
        etInstagramLink.setText(website.getInstagramUrl());

        String headerImageUrl = website.getHeader().getImageUrl();
        if(headerImageUrl != null)
            Picasso.with(getActivity()).load(headerImageUrl).into(ivHeader);

        String logoImageUrl = website.getLogo().getImageUrl();
        if(logoImageUrl != null)
            Picasso.with(getActivity()).load(logoImageUrl).into(ivLogo);
    }

    @Override
    public void submit(final SaveCallback saveCallback) {

        //get current user
        User user = (User) ParseUser.getCurrentUser();
        Website website = user.getWebsite();
        website.setTypeOfBusiness(sprBusinessType.getSelectedItem().toString());
        website.setFacebookUrl(etFacebookLink.getText().toString());
        website.setYelpUrl(etYelpLink.getText().toString());
        website.setTwitterUrl(etTwitterLink.getText().toString());
        website.setInstagramUrl(etInstagramLink.getText().toString());

        ParseObject objectsToSave[] = {user, website};
        incrementNetworkActivityCount();
        ParseHelper.saveObjectsInBackgroundInParallel(objectsToSave,
                new SaveCallback() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode >= REQUEST_CODE_WEB_CONTENT && requestCode <= REQUEST_CODE_WEB_CONTENT + 3) {

            if(resultCode == getActivity().RESULT_OK) {
                checkPages.get(requestCode - REQUEST_CODE_WEB_CONTENT).setVisibility(View.VISIBLE);
                checkAllWebpagesVisited();
            }

            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void checkAllWebpagesVisited() {
        // while none of the input fields are required, check to make sure the user visited all website page views
        // before we allow them to submit
        btnSubmit.setEnabled(true);
        for(ImageView checkPage : checkPages) {
            if(checkPage.getVisibility() != View.VISIBLE)
                btnSubmit.setEnabled(false);
        }
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
