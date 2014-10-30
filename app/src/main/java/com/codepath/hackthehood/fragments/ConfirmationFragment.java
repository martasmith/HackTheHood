package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.FinalWebsiteViewerActivity;
import com.codepath.hackthehood.models.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ConfirmationFragment extends NetworkFragment {

    private ConfirmationViewListener mListener;
    private String mShareMessage;
    private ImageView ivStatus;
    private TextView tvMainText;
    private TextView tvSubText;
    private Button btnShare;
    private Button btnAddAssets;
    @InjectView(R.id.btnToFinalWebsite) Button btnToSeeFinalWebsite;

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachFragment(getParentFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_confirmation, container, false);
        ivStatus = (ImageView) rootView.findViewById(R.id.ivStatus);
        tvMainText = (TextView) rootView.findViewById(R.id.tvMainText);
        tvSubText = (TextView) rootView.findViewById(R.id.tvSubText);
        btnShare = (Button) rootView.findViewById(R.id.btnShare);
        btnAddAssets = (Button) rootView.findViewById(R.id.btnToAssets);

        ButterKnife.inject(this, rootView);
        setUpNextStepListener();
        setUpShareListener();
        btnToSeeFinalWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FinalWebsiteViewerActivity.class));
                getActivity().overridePendingTransition (R.anim.open_right, R.anim.close_left);
            }
        });

        if (savedInstanceState == null) {
            fetch(true);
        }
        return rootView;
    }

    @Override
    protected void doFetch(boolean onlyIfNeeded) {
        super.doFetch(onlyIfNeeded);
        final User user = (User) ParseUser.getCurrentUser();

        GetCallback getCallback =
            new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    didReceiveNetworkException(e);
                    decrementNetworkActivityCount();

                    if(e == null)
                        pushUserToView();
                }
            };

        incrementNetworkActivityCount();
        if(onlyIfNeeded)
            user.fetchIfNeededInBackground(getCallback);
        else
            user.fetchInBackground(getCallback);
    }

    private void pushUserToView() {
        final User user = (User) ParseUser.getCurrentUser();
        int imageResource = R.drawable.ic_success;
        int applicationStatus = user.getApplicationStatus();
        btnToSeeFinalWebsite.setVisibility(View.GONE);

        String actionBarTitle = "";
        String mainText = "";
        String subText = "";
        mShareMessage = "";

        switch (applicationStatus) {
            case User.APPSTATUS_PENDING_REVIEW:
                mainText = "Thank you for your interest!";
                subText = "We will let you know once your application has been approved!";
                actionBarTitle = "Application Submitted";
                mShareMessage = "I just applied to get my website created by Hack the Hood!";
                break;

            case User.APPSTATUS_ACCEPTED:
                mainText = "Congratulations!";
                subText = "We have accepted your application! Please submit assets for your website!";
                actionBarTitle = "Application Accepted";
                btnShare.setVisibility(View.GONE);
                btnAddAssets.setVisibility(View.VISIBLE);
                imageResource = R.drawable.ic_approved;
                break;

            case User.APPSTATUS_DECLINED:
                mainText = "Sorry, you application was denied.";
                subText = "We won't be able to build a website for you at this time! Please feel free to apply again in future.";
                actionBarTitle = "Application Denied";
                btnShare.setVisibility(View.GONE);
                btnAddAssets.setVisibility(View.GONE);
                imageResource = R.drawable.ic_denied;
                //TODO: Add option to Log out at this time
                break;

            case User.APPSTATUS_ASSETS_SUBMITTED:
                mainText = "Thanks for submitting the assets!";
                subText = "Our students will create your website in our upcoming bootcamp! If we have any questions, we will get in touch.";
                actionBarTitle = "Assets Submitted";
                mShareMessage = "I just applied to get my website created by Hack the Hood!";
                btnShare.setVisibility(View.VISIBLE);
                btnAddAssets.setVisibility(View.GONE);
                break;

            case User.APPSTATUS_SITE_COMPLETED:
                mainText = "Congratulations! Your website has been created!";
                subText = "Thank you for your support! Our students have created a beautiful website for you! Check it out!";
                actionBarTitle = "Website Created";
                mShareMessage = "Hack the Hood students created a beautiful website for my business for free! Check it out!";
                btnShare.setVisibility(View.VISIBLE);
                btnAddAssets.setVisibility(View.GONE);
                btnToSeeFinalWebsite.setVisibility(View.VISIBLE);
                //TODO: Add option to see the website
                break;

            default:
                break;
        }

        tvMainText.setText(mainText);
        tvSubText.setText(subText);
        ivStatus.setImageResource(imageResource);
        ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(actionBarTitle);
//      tvSubText.setText(Html.fromHtml("Please <a href=\"http://www.hackthehood.org/contact-us.html\">contact Hack the Hood</a> for more information."));
//      tvSubText.setText(Html.fromHtml("While you're waiting for confirmation, read the <a href=\"http://www.hackthehood.org/blog\">Hack the Hood blog!</a>"));

        updateStateAndNotify();
    }

    private void setUpShareListener() {
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareButtonClicked(view);
            }
        });
    }

    private void shareButtonClicked(View view) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mShareMessage);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void setUpNextStepListener() {
        btnAddAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.startAssetCollection();
                }
            }
        });
    }

    public void onAttachFragment(Fragment fragment) {
        try {
            mListener = (ConfirmationViewListener) fragment;
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

    public interface ConfirmationViewListener {
        public void startAssetCollection();
    }

    private void updateStateAndNotify() {
        final User user = (User) ParseUser.getCurrentUser();
        int applicationStatus = user.getApplicationStatus();

        switch (applicationStatus) {
            case User.APPSTATUS_PENDING_REVIEW:
                user.setApplicationStatus(User.APPSTATUS_ACCEPTED);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            sendPushMessage("Congratulations!", "We have accepted your application! Please submit assets for your website!");
                        }
                    }
                });
                break;

            case User.APPSTATUS_ASSETS_SUBMITTED:
                user.setApplicationStatus(User.APPSTATUS_SITE_COMPLETED);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            sendPushMessage("Congratulations! Your website has been created!", "Thank you for your support! Our students have created a beautiful website for you! Check it out!");
                        }
                    }
                });
                break;

            default:
                break;
        }


    }

    private void sendPushMessage(String title, String message) {

        JSONObject data = null;
        try {
            data = new JSONObject("{\"title\": \"" + title + "\", \"alert\": \"" + message +"\", \"confirm\": \"true\"}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ParsePush push = new ParsePush();
        push.setData(data);
        push.sendInBackground();
    }
}
