package com.codepath.hackthehood.fragments.forms;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.controller.ParseHelper;
import com.codepath.hackthehood.fragments.NetworkFragment;
import com.codepath.hackthehood.models.Address;
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.Website;
import com.codepath.hackthehood.util.MultiSelectionSpinner;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Iterator;


public class UserFragment extends NetworkFragment {

    private BusinessFormListener mListener;

    private EditText etBusinessName,    etBusinessStreet,   etBusinessCity,     etBusinessZip,
                     etBusinessPhone,   etContactName,      etContactPhone,     etContactEmail;
    private MultiSelectionSpinner sprOnlinePresence;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();
        storeCurrentForm();
    }

    public void storeCurrentForm() {
        User user = (User) ParseUser.getCurrentUser();
        if(user != null && user.getWebsite() != null && user.getWebsite().getAddress() != null) {
            pushAllParseFields();
            user.saveEventually();
            user.getWebsite().saveEventually();
            user.getWebsite().getAddress().saveEventually();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onAttachFragment(getParentFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_business_form, container, false);

        etBusinessName      = (EditText) v.findViewById(R.id.etBusinessName);
        etBusinessStreet    = (EditText) v.findViewById(R.id.etBusinessStreet);
        etBusinessCity      = (EditText) v.findViewById(R.id.etBusinessCity);
        etBusinessZip       = (EditText) v.findViewById(R.id.etBusinessZip);
        etBusinessPhone     = (EditText) v.findViewById(R.id.etBusinessPhone);
        sprOnlinePresence   = (MultiSelectionSpinner) v.findViewById(R.id.sprOnlinePresence);
        etContactName       = (EditText) v.findViewById(R.id.etContactName);
        etContactPhone      = (EditText) v.findViewById(R.id.etContactPhone);
        etContactEmail      = (EditText) v.findViewById(R.id.etContactEmail);
        Button btnSubmitBusinessForm = (Button) v.findViewById(R.id.btnSubmit);

        String [] spinnerContentArr = {"Website", "Online Store", "Facebook Page","Twitter Account","Google Listing", "Yelp","Other"};
        sprOnlinePresence.setItems(spinnerContentArr);

        setupSubmitListener(btnSubmitBusinessForm);
        getAllParseFields();

        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle("Business Form");

        return v;
    }

    private void showValidationError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    private void setupSubmitListener(Button btnSubmitBusinessForm) {
        btnSubmitBusinessForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (etBusinessName.getText().toString().isEmpty()) {
                    showValidationError("Business name is required");
                    return;
                }

                if (etBusinessPhone.getText().toString().isEmpty()) {
                    showValidationError("Business phone is required");
                    return;
                }

                if (etContactName.getText().toString().isEmpty()) {
                    showValidationError("Contact name is required");
                    return;
                }

                if (etContactPhone.getText().toString().isEmpty()) {
                    showValidationError("Contact phone is required");
                    return;
                }

                if (etContactEmail.getText().toString().isEmpty()) {
                    showValidationError("Contact email is required");
                    return;
                }

                User user = (User) ParseUser.getCurrentUser();
                user.setApplicationStatus(User.APPSTATUS_PENDING_REVIEW);

                pushAllParseFields();

                ParseObject[] objectsToSave = {user, user.getWebsite(), user.getWebsite().getAddress()};
                incrementNetworkActivityCount();
                v.setEnabled(false);
                ParseHelper.saveObjectsInBackgroundInParallel(objectsToSave, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            v.setEnabled(true);
                            didReceiveNetworkException(e);
                            decrementNetworkActivityCount();

                            if(e == null && mListener != null)
                                mListener.onBusinessFormSubmit();
                        }
                    });
            }
         });
    }

    private void pushAllParseFields() {

        //get current user
        User user = (User) ParseUser.getCurrentUser();
        if(user == null) return;

        //set  Parse user values
        user.setFullName(etContactName.getText().toString());
        user.setEmail(etContactEmail.getText().toString());
        user.setPhoneNumber(etContactPhone.getText().toString());

        //set website values
        Website website = user.getWebsite();
        website.setBusinessName(etBusinessName.getText().toString());
        website.setPhoneNumber(etBusinessPhone.getText().toString());
        website.setOnlinePresenceType(sprOnlinePresence.getSelectedItemsAsString());

        //set business address
        Address businessAddress = website.getAddress();
        businessAddress.setStreetAddress(etBusinessStreet.getText().toString());
        businessAddress.setCity(etBusinessCity.getText().toString());
        businessAddress.setPostalCode(etBusinessZip.getText().toString());
    }

    private void getAllParseFields() {

        incrementNetworkActivityCount();
        final User user = (User) ParseUser.getCurrentUser();
        ParseHelper.fetchObjectsInBackgroundInSerial(true, new Iterator<ParseObject>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index != 3;
            }

            @Override
            public ParseObject next() {
                switch (index++) {
                    default:
                        return user;
                    case 1:
                        return user.getWebsite();
                    case 2:
                        return user.getWebsite().getAddress();
                }
            }

            @Override
            public void remove() {
            }

        }, new GetCallback() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                decrementNetworkActivityCount();
                didReceiveNetworkException(e);
                if (e == null) {
                    Website website = user.getWebsite();
                    if (website == null) {
                        scaffoldUser();
                    } else {
                        populateForm();
                    }
                }
            }
        });
    }

    private void scaffoldUser() {
        User user = (User) ParseUser.getCurrentUser();
        incrementNetworkActivityCount();
        user.addDefaultWebsite(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                decrementNetworkActivityCount();
                didReceiveNetworkException(e);

                if(e == null)
                    populateForm();
            }
        });
    }

    private void populateForm() {
        User user = (User) ParseUser.getCurrentUser();
        Website website = user.getWebsite();
        Address address = website.getAddress();

        etBusinessName.setText(website.getBusinessName());
        etBusinessStreet.setText(address.getStreetAddress());
        etBusinessCity.setText(address.getCity());
        etBusinessZip.setText(address.getPostalCode());
        etBusinessPhone.setText(website.getPhoneNumber());
        sprOnlinePresence.setSelectedItemsAsString(website.getOnlinePresenceType());
        etContactName.setText(user.getFullName());
        etContactPhone.setText(user.getPhoneNumber());
        etContactEmail.setText(user.getEmail());
    }

    public void onAttachFragment(Fragment fragment) {
        try {
            mListener = (BusinessFormListener) fragment;
        } catch (ClassCastException e) {
            throw new ClassCastException(fragment.toString()
                    + " must implement BusinessFormListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface BusinessFormListener {
        public void onBusinessFormSubmit();
    }
}
