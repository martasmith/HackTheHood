package com.codepath.hackthehood.fragments.slides;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.hackthehood.R;

/**
 * ImageSlideFragment displays a full screen background image and a title
 * for the pitch deck activity
 * Use the {@link ImageSlideFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Created by ravi on 10/12/14.
 */
public class ImageSlideFragment extends Fragment {
    private static final String SLIDE_TEXT = "slideText";

    private String mSlideText;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ImageSlideFragment.
     */
    public static ImageSlideFragment newInstance(String slideText) {
        ImageSlideFragment fragment = new ImageSlideFragment();
        Bundle args = new Bundle();
        args.putString(SLIDE_TEXT, slideText);
        fragment.setArguments(args);
        return fragment;
    }
    public ImageSlideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSlideText = getArguments().getString(SLIDE_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_slide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvTitleTextView = (TextView) view.findViewById(R.id.tvSlideText);
        tvTitleTextView.setAlpha(0.0f);
        tvTitleTextView.animate().alpha(1.0f).setDuration(1000);
        tvTitleTextView.setText(mSlideText);
    }
}
