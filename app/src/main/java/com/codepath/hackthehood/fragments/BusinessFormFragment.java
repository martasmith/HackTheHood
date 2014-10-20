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
import com.codepath.hackthehood.models.User;
import com.codepath.hackthehood.models.Website;
import com.codepath.hackthehood.util.MultiSelectionSpinner;
import com.parse.ParseException;
import com.parse.ParseUser;


public class BusinessFormFragment extends Fragment {

    private EditText etBusinessName, etBusinessStreet, etBusinessCity, etBusinessZip,
                     etBusinessPhone,etContactName,etContactPhone,etContactEmail;
    private MultiSelectionSpinner sprOnlinePresence;
    private Button btnSubmitBusinessForm;

    private String businessName, businessStreet,businessCity,businessZip,businessPhone,onlinePresence,contactName,contactPhone,contactEmail;


    public BusinessFormFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPause() {
        readBusinessForm();
        setAllFields();
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_business_form, container, false);
        etBusinessName = (EditText) v.findViewById(R.id.etBusinessName);
        etBusinessStreet = (EditText) v.findViewById(R.id.etBusinessStreet);
        etBusinessCity = (EditText) v.findViewById(R.id.etBusinessCity);
        etBusinessZip = (EditText) v.findViewById(R.id.etBusinessZip);
        etBusinessPhone = (EditText) v.findViewById(R.id.etBusinessPhone);
        sprOnlinePresence = (MultiSelectionSpinner)  v.findViewById(R.id.sprOnlinePresence);
        etContactName = (EditText) v.findViewById(R.id.etContactName);
        etContactPhone = (EditText) v.findViewById(R.id.etContactPhone);
        etContactEmail = (EditText) v.findViewById(R.id.etContactEmail);
        btnSubmitBusinessForm = (Button)  v.findViewById(R.id.btnSubmit);

        String [] spinnerContentArr = {"Website", "Online Store", "Facebook Page","Twitter Account","Google Listing", "Yelp","Other"};
        sprOnlinePresence.setItems(spinnerContentArr);

        setupSubmitListener();

        getAllFields();
        writeBusinessForm();

        return v;
    }

    private void setupSubmitListener() {
        btnSubmitBusinessForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitBusinessForm()) {
                    Intent i = new Intent(getActivity(), ConfirmationActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void writeBusinessForm() {
        etBusinessName.setText(businessName);
        etBusinessStreet.setText(businessStreet);
        etBusinessCity.setText(businessCity);
        etBusinessZip.setText(businessZip);
        etBusinessPhone.setText(businessPhone);
        sprOnlinePresence.setSelectedItemsAsString(onlinePresence);
        etContactName.setText(contactName);
        etContactPhone.setText(contactPhone);
        etContactEmail.setText(contactEmail);
    }

    private void readBusinessForm() {
        businessName = etBusinessName.getText().toString();
        businessStreet = etBusinessStreet.getText().toString();
        businessCity = etBusinessCity.getText().toString();
        businessZip = etBusinessZip.getText().toString();
        businessPhone = etBusinessPhone.getText().toString();
        onlinePresence = sprOnlinePresence.getSelectedItemsAsString();
        contactName = etContactName.getText().toString();
        contactPhone = etContactPhone.getText().toString();
        contactEmail = etContactEmail.getText().toString();

    }

    private boolean submitBusinessForm() {

        readBusinessForm();

        if (businessName.isEmpty()) {
            Toast.makeText(getActivity(), "Business name is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (businessPhone.isEmpty()) {
            Toast.makeText(getActivity(), "Business phone is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (contactName.isEmpty()) {
            Toast.makeText(getActivity(), "Contact name is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (contactEmail.isEmpty()) {
            Toast.makeText(getActivity(), "Contact email is required", Toast.LENGTH_LONG).show();
            return false;
        } else if (contactPhone.isEmpty()) {
            Toast.makeText(getActivity(), "Contact phone is required", Toast.LENGTH_LONG).show();
            return false;
        } else {

            setAllFields();
            return true;
        }
    }

    private void setAllFields() {
        //get current user
        User user = (User) ParseUser.getCurrentUser();

        //set  Parse user values
        user.setFullName(contactName);
        user.setEmail(contactEmail);
        user.setPhoneNumber(contactPhone);
        user.saveEventually();

        //set website values
        Website website = user.getWebsite();
        website.setBusinessName(businessName);
        website.setPhoneNumber(businessPhone);
        website.setOnlinePresenceType(onlinePresence);
        website.saveEventually();

        //set business address
        Address businessAddress = website.getAddress();
        businessAddress.setStreetAddress(businessStreet);
        businessAddress.setCity(businessCity);
        businessAddress.setPostalCode(businessZip);
        businessAddress.saveEventually();
    }

    private void getAllFields() {
        //get current user
        User user = (User) ParseUser.getCurrentUser();

        //set  Parse user values
        contactName = user.getFullName();
        contactEmail = user.getEmail();
        contactPhone = user.getPhoneNumber();

        //set website values
        Website website = user.getWebsite();
        try {
            website.fetchIfNeeded();
        } catch (ParseException e) {
            return;
        }
        businessName = website.getBusinessName();
        businessPhone = website.getPhoneNumber();
        onlinePresence = website.getOnlinePresenceType();

        //set business address
        Address businessAddress = website.getAddress();
        businessStreet = businessAddress.getStreetAddress();
        businessCity = businessAddress.getCity();
        businessZip = businessAddress.getPostalCode();
    }

}
