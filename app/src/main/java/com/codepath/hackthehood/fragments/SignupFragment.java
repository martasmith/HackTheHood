package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.BusinessFormActivity;
import com.codepath.hackthehood.models.User;
import com.parse.ParseException;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignupFragment extends android.support.v4.app.Fragment {

    private EditText etEmail, etEmailConfirmation, etPassword;

    public SignupFragment() {
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        // grab views
        Button btnSignUp = (Button)view.findViewById(R.id.btnSignUp);
        etEmail = (EditText)view.findViewById(R.id.etEmail);
        etEmailConfirmation = (EditText)view.findViewById(R.id.etEmailConfirmation);
        etPassword = (EditText)view.findViewById(R.id.etPassword);

        // hook up to capture presses on the sign-up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        return view;
    }

    private void signUp() {
        // validate email
        String emailAddress = etEmail.getText().toString();
        if(!emailAddress.equals(etEmailConfirmation.getText().toString())) {
            Toast.makeText(getActivity(), "E-mail addresses don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        final User user = new User();
        user.setUsername(emailAddress);
        user.setEmail(emailAddress);
        user.setPassword(etPassword.getText().toString());
        user.addDefaultWebsite(
                new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            showException(e);
                        } else {
                            user.signUpInBackground(new SignUpCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Intent intent = new Intent(getActivity(), BusinessFormActivity.class);
                                        startActivity(intent);
                                    } else {
                                        showException(e);
                                    }
                                }
                            });

                        }
                    }
                });
    }

    private void showException(Exception e) {
        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

}
