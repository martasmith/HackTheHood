package com.codepath.hackthehood.fragments.slides;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.hackthehood.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * Created by ravi on 10/24/14.
 */
public class GetBusinessOnlineSlideFragment extends Fragment {


    public GetBusinessOnlineSlideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_business_online_slide, container, false);
    }


}
