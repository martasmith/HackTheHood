package com.codepath.hackthehood.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.AssetCollectionActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AssetCollectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AssetCollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AssetCollectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText etFacebookLink, etYelpLink, etTwitterLink, etInstagramLink;
    private Spinner sprBusinessType;
    private ImageView ivHeader, ivLogo, ivMore;
    private Button btnPage1,btnPage2,btnPage3, btnSubmit, btnNextStep2;

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssetCollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssetCollectionFragment newInstance(String param1, String param2) {
        AssetCollectionFragment fragment = new AssetCollectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public AssetCollectionFragment() {
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
        //return inflater.inflate(R.layout.fragment_asset_collection, container, false);
        View v = inflater.inflate(R.layout.fragment_asset_collection, container, false);
        sprBusinessType = (Spinner) v.findViewById(R.id.sprBusinessType);
        etFacebookLink = (EditText) v.findViewById(R.id.etFacebookLink);
        etYelpLink = (EditText) v.findViewById(R.id.etYelpLink);
        etTwitterLink = (EditText) v.findViewById(R.id.etTwitterLink);
        etInstagramLink = (EditText) v.findViewById(R.id.etInstagramLink);
        ivLogo = (ImageView) v.findViewById(R.id.imgLogo);
        ivHeader = (ImageView) v.findViewById(R.id.imgHeader);
        ivMore = (ImageView) v.findViewById(R.id.imgMore);
        btnPage1 = (Button) v.findViewById(R.id.btnPage1);
        btnPage2 = (Button) v.findViewById(R.id.btnPage2);
        btnPage3 = (Button) v.findViewById(R.id.btnPage3);
        btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
        btnNextStep2 = (Button) v.findViewById(R.id.btnNextStep2);
        setUpNextStepListener();
        return v;
    }

    private void setUpNextStepListener() {
        btnNextStep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "You have clicked on Next Step...", Toast.LENGTH_SHORT).show();
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
