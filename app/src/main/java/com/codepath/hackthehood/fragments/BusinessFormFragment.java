package com.codepath.hackthehood.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.hackthehood.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BusinessFormFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BusinessFormFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class BusinessFormFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tvBformTime, tvBusinessInfo, tvContactInfo;
    private EditText etBusinessName, etBusinessStreet, etBusinessCity, etBusinessZip,
                     etBusinessPhone,etContactName,etContactPhone,etContactEmail;
    private Spinner sprOnlinePresence;

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusinessFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusinessFormFragment newInstance(String param1, String param2) {
        BusinessFormFragment fragment = new BusinessFormFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public BusinessFormFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_business_form, container, false);
        View v = inflater.inflate(R.layout.fragment_business_form, container, false);
        etBusinessName = (EditText) v.findViewById(R.id.etBusinessName);
        etBusinessStreet = (EditText) v.findViewById(R.id.etBusinessStreet);
        etBusinessCity = (EditText) v.findViewById(R.id.etBusinessCity);
        etBusinessZip = (EditText) v.findViewById(R.id.etBusinessZip);
        sprOnlinePresence = (Spinner) v.findViewById(R.id.sprOnlinePresence);
        etContactName = (EditText) v.findViewById(R.id.etContactName);
        etContactPhone = (EditText) v.findViewById(R.id.etContactPhone);
        etContactEmail = (EditText) v.findViewById(R.id.etContactEmail);
        return v;
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
