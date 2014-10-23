package com.codepath.hackthehood.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.models.User;
import com.parse.ParseUser;

/**
 * Created by ravi on 10/22/14.
 */
public class ApplicationFragment extends Fragment implements BusinessFormFragment.OnBusinessFormSubmitListener {
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
        } catch (Exception e) {}

        switch(user.getApplicationStatus()) {
            case User.APPSTATUS_STARTED:
                BusinessFormFragment businessFormFragment = new BusinessFormFragment();
                fragmentTransaction.replace(R.id.flApplicationFragmentContainer, businessFormFragment);
                break;

            case User.APPSTATUS_ACCEPTED:
                AssetCollectionFragment assetCollectionFragment = new AssetCollectionFragment();
                fragmentTransaction.replace(R.id.flApplicationFragmentContainer, assetCollectionFragment);
                break;

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
}
