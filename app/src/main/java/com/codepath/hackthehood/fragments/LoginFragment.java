package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.BusinessFormActivity;
import com.codepath.hackthehood.activities.ConfirmationActivity;
import com.codepath.hackthehood.models.User;


public class LoginFragment extends android.support.v4.app.Fragment {

    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final Button btnLogin = (Button)view.findViewById(R.id.btnLogin);
        final EditText etEmail = (EditText)view.findViewById(R.id.etEmail);

        // XML onClicks go to the activity, not the fragment. Setting things up programmatically
        // seems to be the recommended way to capture "clicks" here (no, really)
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // find or create user; we're still waiting on backend decisions
                String emailAddress = etEmail.getText().toString();
                User user = User.findUserByEmailAddress(emailAddress);
                if(user == null) {
                    user = new User();
                    user.setEmailAddress(emailAddress);
                    user.save();
                }

                Intent intent = new Intent(getActivity(), BusinessFormActivity.class);
                intent.putExtra(BusinessFormActivity.USER, user);
                startActivity(intent);
            }
        });

        return view;
    }
}
