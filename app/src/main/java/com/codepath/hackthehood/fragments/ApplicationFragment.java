package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.models.User;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ravi on 10/22/14.
 */
public class ApplicationFragment extends Fragment implements BusinessFormFragment.BusinessFormListener,
        WebsiteCollectionFragment.WebsiteInfoListener,
        ConfirmationFragment.ConfirmationViewListener,
        WebsitePageCollectionFragment.WebpageFormListener {

    private HashMap<Integer, WebsitePageCollectionFragment> mWebpageCollectionFragments;
    private WebsiteCollectionFragment mWebsiteCollectionFragment;
    public ApplicationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_application, container, false);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            setFragmentBasedOnState();
        }
    }

    private void setFragmentBasedOnState() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        // check whether we should be skipping ahead
        User user = (User) ParseUser.getCurrentUser();
        try {
            user.fetch();
        } catch (Exception e) {
        }

        switch (user.getApplicationStatus()) {
            case User.APPSTATUS_STARTED:
                BusinessFormFragment businessFormFragment = new BusinessFormFragment();
                fragmentTransaction.replace(R.id.flApplicationFragmentContainer, businessFormFragment);
                break;

            case User.APPSTATUS_ACCEPTED:
            case User.APPSTATUS_PENDING_REVIEW:
            case User.APPSTATUS_ASSETS_SUBMITTED:
            case User.APPSTATUS_DECLINED:
            case User.APPSTATUS_SITE_COMPLETED:
                ConfirmationFragment confirmationFragment = new ConfirmationFragment();
                fragmentTransaction.replace(R.id.flApplicationFragmentContainer, confirmationFragment);
                break;

            default:
                break;
        }

        fragmentTransaction.commit();
    }

    @Override
    public void onBusinessFormSubmit() {
        setFragmentBasedOnState();
        getView().invalidate();
    }

    @Override
    public void collectPageInfo(String title, int pageIndex) {
        if (mWebpageCollectionFragments == null) {
            mWebpageCollectionFragments = new HashMap<Integer, WebsitePageCollectionFragment>();
        }

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        WebsitePageCollectionFragment websitePageCollectionFragment = mWebpageCollectionFragments.get(pageIndex);
        if(websitePageCollectionFragment == null) {
            websitePageCollectionFragment = WebsitePageCollectionFragment.newInstance(title, pageIndex);
            mWebpageCollectionFragments.put(pageIndex, websitePageCollectionFragment);
        }

        fragmentTransaction.replace(R.id.flApplicationFragmentContainer, websitePageCollectionFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onWebsiteInfoFormSubmit() {
        setFragmentBasedOnState();
        getView().invalidate();
    }

    @Override
    public void startAssetCollection() {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (mWebsiteCollectionFragment == null) {
            mWebsiteCollectionFragment = new WebsiteCollectionFragment();
        }

        fragmentTransaction.replace(R.id.flApplicationFragmentContainer, mWebsiteCollectionFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onWebpageFormSubmit(int pageIndex) {
        if (mWebsiteCollectionFragment != null) {
            mWebsiteCollectionFragment.collectedInfoForPage(pageIndex);
            startAssetCollection();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
