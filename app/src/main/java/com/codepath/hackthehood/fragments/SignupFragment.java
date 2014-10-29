package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.MainNavigationActivity;
import com.codepath.hackthehood.models.User;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupFragment extends android.support.v4.app.Fragment {

    private EditText etEmail, etEmailConfirmation, etPassword;
    private ProgressBar pbLoading;

    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        // grab views
        Button btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etEmailConfirmation = (EditText) view.findViewById(R.id.etEmailConfirmation);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);

        // hook up to capture presses on the sign-up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp((Button) view);
            }
        });

        return view;
    }

    private void signUp(final Button button) {
        // validate email
        String emailAddress = etEmail.getText().toString();
        if (!emailAddress.equals(etEmailConfirmation.getText().toString())) {
            Toast.makeText(getActivity(), "E-mail addresses don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        // disable button, enable spinner
        button.setEnabled(false);
        pbLoading.setVisibility(View.VISIBLE);

        final ParseUser user = new ParseUser();
        user.setUsername(emailAddress);
        user.setEmail(emailAddress);
        user.setPassword(etPassword.getText().toString());
        user.signUpInBackground(
                new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        pbLoading.setVisibility(View.INVISIBLE);
                        button.setEnabled(true);
                        if (e != null) {
                            showException(e);
                        } else {
                            startActivity(new Intent(getActivity(), MainNavigationActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                    }
                });
    }

    private void showException(Exception e) {
        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

}
