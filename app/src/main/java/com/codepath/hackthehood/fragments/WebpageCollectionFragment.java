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

import com.codepath.hackthehood.R;
import com.codepath.hackthehood.activities.WebpageCollectionActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebpageCollectionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebpageCollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class WebpageCollectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String tickImgName;
    private EditText etPageText, etDesignerNotes;
    private ImageView ivFileUpload1,ivFileUpload2,ivFileUpload3;
    private Button btnAddSite;

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment WebpageCollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebpageCollectionFragment newInstance(String tickImgName) {
        WebpageCollectionFragment fragment = new WebpageCollectionFragment();
        Bundle args = new Bundle();
        args.putString("tickImgName", tickImgName);
        fragment.setArguments(args);
        return fragment;
    }
    public WebpageCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tickImgName = getArguments().getString("tickImgName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_webpage_collection,container,false);
        etPageText = (EditText) v.findViewById(R.id.etPageText);
        etDesignerNotes = (EditText) v.findViewById(R.id.etDesignerNotes);
        ivFileUpload1 = (ImageView) v.findViewById(R.id.imgFile1);
        ivFileUpload2 = (ImageView) v.findViewById(R.id.imgFile2);
        ivFileUpload3 = (ImageView) v.findViewById(R.id.imgFile3);
        btnAddSite = (Button) v.findViewById(R.id.btnAddSite);
        setupAddSiteListener();
        return v;
    }

    private void setupAddSiteListener() {
        btnAddSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("tickImgName", tickImgName);
                getActivity().setResult(getActivity().RESULT_OK, data);
                getActivity().finish();

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
