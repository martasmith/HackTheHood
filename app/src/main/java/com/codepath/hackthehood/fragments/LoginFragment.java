package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


public class LoginFragment extends android.support.v4.app.Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final Button btnLogin = (Button)view.findViewById(R.id.btnLogin);
        final Button btnForgottenPassword = (Button)view.findViewById(R.id.btnForgottenPassword);
        final EditText etEmail = (EditText)view.findViewById(R.id.etEmail);
        final EditText etPassword = (EditText)view.findViewById(R.id.etPassword);
        final ProgressBar pbLoading = (ProgressBar)view.findViewById(R.id.pbLoading);

        // XML onClicks go to the activity, not the fragment. Setting things up programmatically
        // seems to be the recommended way to capture "clicks" here (no, really)
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailAddress = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(emailAddress)) {
                    Crouton.showText(getActivity(), "Email address cannot be empty", Style.ALERT);
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Crouton.showText(getActivity(), "Password cannot be empty", Style.ALERT);
                    return;
                }

                btnLogin.setEnabled(false);
                btnForgottenPassword.setEnabled(false);
                pbLoading.setVisibility(View.VISIBLE);

                User.logInInBackground(emailAddress, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if(e == null) {
                            startActivity(new Intent(getActivity(), MainNavigationActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        } else {
                            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            btnLogin.setEnabled(true);
                            btnForgottenPassword.setEnabled(true);
                            pbLoading.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });

        btnForgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(emailAddress)) {
                    Crouton.showText(getActivity(), "Email address cannot be empty", Style.ALERT);
                    return;
                }
                
                ParseUser.requestPasswordResetInBackground(emailAddress);
                Toast.makeText(getActivity(), "A password reset email has been sent to " + emailAddress, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
