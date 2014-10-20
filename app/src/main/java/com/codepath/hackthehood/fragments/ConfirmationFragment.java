package com.codepath.hackthehood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.AssetCollectionActivity;
import com.codepath.hackthehood.models.User;
import com.parse.ParseException;
import com.parse.ParseUser;


public class ConfirmationFragment extends Fragment {


    private String mShareMessage;
    private ImageView ivStatus;
    private TextView tvMainText;
    private TextView tvSubText;
    private Button btnShare;
    private Button btnAddAssets;

    public ConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        adaptViewToCurrentStatus();
        setUpNextStepListener();
        setUpShareListener();
        return rootView;
    }

    private void adaptViewToCurrentStatus() {
        //get current user
        User user = (User) ParseUser.getCurrentUser();
        try {
            user.fetch();
        } catch (ParseException e) {
            return;
        }
        int imageResource = R.drawable.ic_success;
        int applicationStatus = user.getApplicationStatus();
        
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
                //TODO: Add option to see the website
                break;

            default:
                break;
        }

        tvMainText.setText(mainText);
        tvSubText.setText(subText);
        ivStatus.setImageResource(imageResource);
        getActivity().getActionBar().setTitle(actionBarTitle);
//      tvSubText.setText(Html.fromHtml("Please <a href=\"http://www.hackthehood.org/contact-us.html\">contact Hack the Hood</a> for more information."));
//      tvSubText.setText(Html.fromHtml("While you're waiting for confirmation, read the <a href=\"http://www.hackthehood.org/blog\">Hack the Hood blog!</a>"));
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
                Intent i = new Intent(getActivity(), AssetCollectionActivity.class);
                startActivity(i);
            }
        });
    }
}
