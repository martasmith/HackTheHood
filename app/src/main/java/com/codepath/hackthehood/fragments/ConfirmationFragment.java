package com.codepath.hackthehood.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.AssetCollectionActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfirmationFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ConfirmationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String shareMsg;
    private TextView tvConfComplete, tvConfStatus;
    private ImageView ivStatus;
    private Button btnToAssets, btnShareConfirm;

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmationFragment newInstance(String param1, String param2) {
        ConfirmationFragment fragment = new ConfirmationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_confirmation, container, false);
        View v = inflater.inflate(R.layout.fragment_confirmation, container, false);
        tvConfComplete = (TextView) v.findViewById(R.id.tvConfComplete);
        tvConfStatus = (TextView) v.findViewById(R.id.tvConfStatus);
        ivStatus = (ImageView) v.findViewById(R.id.ivStatus);
        btnToAssets = (Button) v.findViewById(R.id.btnToAssets);
        btnShareConfirm = (Button) v.findViewById(R.id.btnShare);
        populateCurrentStatus();
        setUpNextStepListener();
        setUpShareListener();
        return v;

    }


    private void setUpShareListener() {
        btnShareConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareMsg = "I just signed up for a <b>free website</b> with Hack the Hood!";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "I'm getting a free website");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(shareMsg));
                startActivity(Intent.createChooser(sharingIntent, "Share using"));

            }
        });
    }


    private void populateCurrentStatus() {
        // This method will change the status text and image based on the application status
        // for now, it's just a dummy
        String approvalStat = " <font color=\"red\"><b>Approved!</b></font>";
        tvConfStatus.setText(Html.fromHtml(tvConfStatus.getText() + approvalStat));
        // add conditional statement that changes the image based on the status
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

    /*

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    */

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    */

}
