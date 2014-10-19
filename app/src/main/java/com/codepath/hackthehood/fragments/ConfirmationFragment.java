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
import com.parse.ParseUser;


public class ConfirmationFragment extends Fragment {


    private String shareMsg;
    private TextView tvConfComplete, tvConfStatus,tvStatusText;
    private ImageView ivStatus;
    private Button btnToAssets, btnShareConfirm;


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
        View v = inflater.inflate(R.layout.fragment_confirmation, container, false);
        tvConfComplete = (TextView) v.findViewById(R.id.tvConfComplete);
        tvConfStatus = (TextView) v.findViewById(R.id.tvConfStatus);
        ivStatus = (ImageView) v.findViewById(R.id.ivStatus);
        btnToAssets = (Button) v.findViewById(R.id.btnToAssets);
        btnShareConfirm = (Button) v.findViewById(R.id.btnShare);
        tvStatusText = (TextView) v.findViewById(R.id.tvStatusText);
        tvStatusText.setMovementMethod(LinkMovementMethod.getInstance());

        populateCurrentStatus();
        setUpNextStepListener();
        setUpShareListener();
        return v;

    }

    private void setUpShareListener() {
        btnShareConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellAFriend();
            }
        });
    }

    private void tellAFriend() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMsg);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    private void populateCurrentStatus() {
        //get current user
        User user = (User) ParseUser.getCurrentUser();
        //User.ApplicationStatus applicationStatus = user.getApplicationStatus();

        //hard-coded manual testing of all use-cases
        String applicationStatus = "ACCEPTED";
        //String applicationStatus = "PENDING_REVIEW";
        //String applicationStatus = "DECLINED";
        //String applicationStatus = "SITE_COMPLETED";

        if (applicationStatus.equals("PENDING_REVIEW")) {
            fillCurrentStatusValues("Pending Review",R.drawable.ic_under_review);
        } else if (applicationStatus.equals("ACCEPTED")) {
            fillCurrentStatusValues("Accepted",R.drawable.ic_approved);
        } else if (applicationStatus.equals("DECLINED")) {
            fillCurrentStatusValues("Declined",R.drawable.ic_denied);
        } else if (applicationStatus.equals("SITE_COMPLETED")) {
            fillCurrentStatusValues("Site completed",R.drawable.ic_complete);
        }
    }

    private void fillCurrentStatusValues(String status, int imageResource) {
        String approvalStat = " <font color=\"red\"><b>"+status+"</b></font>";
        tvConfStatus.setText(Html.fromHtml(tvConfStatus.getText() + approvalStat));
        ivStatus.setImageResource(imageResource);
        // now fill the bottom of the page with relevant data
        if (status.equals("Pending Review")) {
            shareMsg = "I just signed up for a free website with Hack the Hood!";
            btnShareConfirm.setVisibility(View.VISIBLE);
            tvStatusText.setText(Html.fromHtml("While you're waiting for confirmation, read the <a href=\"http://www.hackthehood.org/blog\">Hack the Hood blog!</a>"));
        } else if (status.equals("Declined")) {
            tvStatusText.setText(Html.fromHtml("Please <a href=\"http://www.hackthehood.org/contact-us.html\">contact Hack the Hood</a> for more information."));
        } else if (status.equals("Accepted")) {
            shareMsg = "I just got accepted for a free website with Hack the Hood!";
            btnToAssets.setVisibility(View.VISIBLE);
        } else if (status.equals("Site completed")) {
            shareMsg = "I just received my free website from Hack the Hood!";
            btnShareConfirm.setVisibility(View.VISIBLE);
        }

    }

    private void setUpNextStepListener() {
        btnToAssets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AssetCollectionActivity.class);
                startActivity(i);
            }
        });
    }

}
