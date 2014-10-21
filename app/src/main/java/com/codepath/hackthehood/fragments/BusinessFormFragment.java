package com.codepath.hackthehood.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.ConfirmationActivity;
import com.codepath.hackthehood.models.Address;
import com.codepath.hackthehood.models.ParseHelper;
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.Website;
import com.codepath.hackthehood.util.MultiSelectionSpinner;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Iterator;


public class BusinessFormFragment extends Fragment {

    private EditText etBusinessName, etBusinessStreet, etBusinessCity, etBusinessZip,
                     etBusinessPhone,etContactName,etContactPhone,etContactEmail;
    private MultiSelectionSpinner sprOnlinePresence;

    public BusinessFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        super.onPause();

        User user = (User) ParseUser.getCurrentUser();
        if(user == null) return;

        pushAllParseFields();
        user.saveEventually();
        user.getWebsite().saveEventually();
        user.getWebsite().getAddress().saveEventually();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        return v;
    }

    private void setupSubmitListener(Button btnSubmitBusinessForm) {
        btnSubmitBusinessForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBusinessForm();
                }

        });
    }

    private boolean submitBusinessForm() {

        if (etBusinessName.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Business name is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (etBusinessPhone.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Business phone is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (etContactName.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Contact name is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (etContactEmail.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Contact email is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (etContactPhone.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Contact phone is required", Toast.LENGTH_LONG).show();
            return false;
        } else {

            User user = (User) ParseUser.getCurrentUser();
            user.setApplicationStatus(User.APPSTATUS_PENDING_REVIEW);

            pushAllParseFields();

            ParseObject[] objectsToSave = {user, user.getWebsite(), user.getWebsite().getAddress()};
            beginLoading();
            ParseHelper.saveObjectsInBackgroundInParallel(objectsToSave, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null) {
                                didReceiveParseException(e);
                                endLoading();
                            } else {
                                Intent i = new Intent(getActivity(), ConfirmationActivity.class);
                                startActivity(i);
                            }
                        }
                    });

            return true;
        }
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
        beginLoading();
        final User user = (User) ParseUser.getCurrentUser();
        ParseHelper.fetchObjectsInBackgroundInSerial(new Iterator<ParseObject>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index != 3;
            }

            @Override
            public ParseObject next() {
                switch(index) {
                    default: return user;
                    case 1: return user.getWebsite();
                    case 2: return user.getWebsite().getAddress();
                }
            }

            @Override
            public void remove() {

            }
        }, new GetCallback() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e != null) {
                    endLoading();
                    didReceiveParseException(e);
                    return;
                }

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
        });
    }

    // TODO: throw these three up to the activity
    private void didReceiveParseException(com.parse.ParseException e) {

    }

    private void beginLoading() {

    }

    private void endLoading() {

    }

}
